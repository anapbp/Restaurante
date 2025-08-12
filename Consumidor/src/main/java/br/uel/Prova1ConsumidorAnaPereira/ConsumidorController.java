package br.uel.Prova1ConsumidorAnaPereira;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ConsumidorController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    private static final String SESSION_ITENS = "sessionItens";

    @GetMapping("/exibirRestaurantes")
    public String exibirRestaurantes(Model model) {
        List<Restaurante> restaurantes = (List<Restaurante>) restauranteRepository.findAll();
        model.addAttribute("restaurantes", restaurantes);
        return "exibirRestaurantes";
    }

    @GetMapping("/exibirItens/{id}")
    public String exibirItensCardapio(@PathVariable("id") int id, Model model) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("O id do restaurante é inválido: " + id));
        List<ItemCardapio> itensCardapio = restaurante.getItensRestaurante();
        model.addAttribute("restaurante", restaurante);
        model.addAttribute("itensCardapio", itensCardapio);
        return "exibirItens";
    }

    @GetMapping("/adicionarPedido/{id}")
    public String adicionarPedido(@PathVariable("id") int id, HttpServletRequest request){
        ItemCardapio item = itemCardapioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("O id do item é inválido: " + id));


        List <ItemCardapio> listaItens = (List<ItemCardapio>) request.getSession().getAttribute(SESSION_ITENS);

        if(CollectionUtils.isEmpty(listaItens)){
            listaItens = new ArrayList<>();
        }

        listaItens.add(item);
        request.getSession().setAttribute(SESSION_ITENS, listaItens);

        return "redirect:/listaItens";

    }

    @GetMapping("/listaItens")
    public String exibirListaItens(Model model, HttpServletRequest request) {
        List<ItemCardapio> listaItens = (List<ItemCardapio>) request.getSession().getAttribute(SESSION_ITENS);
        Map<ItemCardapio, Restaurante> mapaItensRestaurante = calcularItensRestaurante(listaItens);
        Map<ItemCardapio, Integer> mapaItensQuantidade = calcularQuantidadeItensPedido(listaItens);
        double total = calcularValorTotalPedido(listaItens);

        model.addAttribute("mapaItensQuantidade", mapaItensQuantidade);
        model.addAttribute("mapaItensRestaurante", mapaItensRestaurante);
        model.addAttribute("totalPedido", total);
        return "listaItens";
    }

    private double calcularValorTotalPedido(List<ItemCardapio> listaItens) {
        double total = 0.0;
        if (!CollectionUtils.isEmpty(listaItens)) {
            for (ItemCardapio item : listaItens) {
                total += item.getPreco();
            }
        }
        return total;
    }

    private Map<ItemCardapio, Integer> calcularQuantidadeItensPedido(List<ItemCardapio> listaItens) {
        Map<ItemCardapio, Integer> mapaQuantidade = new HashMap<>();
        if (!CollectionUtils.isEmpty(listaItens)) {
            for (ItemCardapio item : listaItens) {
                if (mapaQuantidade.containsKey(item)) {
                    mapaQuantidade.put(item, mapaQuantidade.get(item) + 1);
                } else {
                    mapaQuantidade.put(item, 1);
                }
            }
        }
        return mapaQuantidade;
    }

    private Map<ItemCardapio, Restaurante> calcularItensRestaurante(List<ItemCardapio> listaItens) {
        Map<ItemCardapio, Restaurante> mapaItensRestaurante = new HashMap<>();
        if (!CollectionUtils.isEmpty(listaItens)) {
            for (ItemCardapio item : listaItens) {
                mapaItensRestaurante.put(item, item.getRestaurante());
            }
        }
        return mapaItensRestaurante;
    }

    @GetMapping("/removerPedido/{id}")
    public String removerPedido(@PathVariable("id") int id, HttpServletRequest request) {
        List<ItemCardapio> listaItens = (List<ItemCardapio>) request.getSession().getAttribute(SESSION_ITENS);
        ItemCardapio itemParaRemover = null;
        if (!CollectionUtils.isEmpty(listaItens)) {
            for (ItemCardapio item : listaItens) {
                if (item.getId() == id) {
                    itemParaRemover = item;
                    break;
                }
            }
            if (itemParaRemover != null) {
                listaItens.remove(itemParaRemover);
                request.getSession().setAttribute(SESSION_ITENS, listaItens);
            }
        }
        return "redirect:/listaItens";
    }

    @GetMapping("/removerItemTipoPedido/{id}")
    public String removerItemTipoPedido(@PathVariable("id") int id, HttpServletRequest request) {
        List<ItemCardapio> listaItens = (List<ItemCardapio>) request.getSession().getAttribute(SESSION_ITENS);
        List<ItemCardapio> itensParaRemover = new ArrayList<>();
        if (!CollectionUtils.isEmpty(listaItens)) {
            for (ItemCardapio item : listaItens) {
                if (item.getId() == id) {
                    itensParaRemover.add(item);
                }
            }
            listaItens.removeAll(itensParaRemover);
            request.getSession().setAttribute(SESSION_ITENS, listaItens);
        }
        return "redirect:/listaItens";
    }
}