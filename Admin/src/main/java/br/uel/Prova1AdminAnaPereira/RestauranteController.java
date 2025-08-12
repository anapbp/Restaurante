    package br.uel.Prova1AdminAnaPereira;

    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.ModelAttribute;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.ui.Model;

    import java.util.List;

    @Controller
    public class RestauranteController {

        @Autowired
        RestauranteRepository restauranteRepository;
        @Autowired
        ItemCardapioRepository itemCardapioRepository;

        @GetMapping("adicionarRestaurante")
        public String exibirPaginaAdicionarRestauranteNovo(Restaurante restaurante){return "addRestaurante";}

        @PostMapping("addRestaurante")
        public String adicionarRestauranteNovo(@Valid Restaurante restaurante, BindingResult result){
            if(result.hasErrors()){
                return "adicionarRestaurante";
            }

            restauranteRepository.save(restaurante);
            return "redirect:/restaurantesAdministracao";
        }

        @GetMapping("/restaurantesAdministracao")
        public String exibirRestaurantesAdministracao(Model model){
            model.addAttribute("restaurantes", restauranteRepository.findAll());
            return "restaurantesAdministracao";
        }

        @GetMapping("/atualizarRestaurante/{id}")
        public String exibirAtualizarRestaurante(@PathVariable("id") int id, Model model){
            Restaurante restaurante = restauranteRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("O id do restaurante é invalido:" + id));
            model.addAttribute("restaurante", restaurante);
            model.addAttribute("restauranteNome", restaurante.getNome());
            model.addAttribute("restauranteId", restaurante.getId());
            return "atualizarRestaurante";
        }

        @PostMapping("atualizarRestaurante/{id}")
        public String atualizarRestaurante(@PathVariable("id") int id, @Valid Restaurante restaurante, BindingResult result, Model model){
            if(result.hasErrors()){
                restaurante.setId(id);
                return "atualizarRestaurante";
            }

            restauranteRepository.save(restaurante);
            return"redirect:/restaurantesAdministracao";
        }

        @GetMapping("/removerRestaurante/{id}")
        public String removerRestaurante(@PathVariable("id") int id){
            Restaurante restaurante = restauranteRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("O id do restaurante é invalido: " + id));
            restauranteRepository.delete(restaurante);
            return "redirect:/restaurantesAdministracao";
        }

        @GetMapping("/adicionarItemRestaurante/{id}")
        public String exibirAdicionarItem(@PathVariable ("id") int id, Model model){
            Restaurante restaurante = restauranteRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("O id do restaurante é invalido: " + id));
            ItemCardapio itemAtual = new ItemCardapio();
            itemAtual.setRestaurante(restaurante);

            model.addAttribute("id", id);
            model.addAttribute("itemAtual", itemAtual);
            return "adicionarItemRestaurante";
        }

        @PostMapping("/adicionarItemCardapio/{id}")
        public String adicionarItemCardapio(@PathVariable("id") int id, @ModelAttribute ItemCardapio itemAtual){
            Restaurante restaurante = restauranteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("O id do restaurante é invalido: " + id));

            if(itemAtual.getRestaurante() != null){
                return "redirect:/adicionarItemRestaurante";
            }
            else{
                itemAtual.setRestaurante(restaurante);
                itemCardapioRepository.save(itemAtual);
            }

            return "redirect:/mostrarItensRestaurante/"+restaurante.getId();
        }

        @GetMapping("/mostrarItensRestaurante/{id}")
        public String mostrarItensRestaurante(@PathVariable("id") int id, Model model){
            Restaurante restauranteAtual = restauranteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("O id do restaurante é invalido: " + id));
            List<ItemCardapio> cardapioList = (List<ItemCardapio>)itemCardapioRepository.findByRestauranteId(id);
            model.addAttribute("cardapio", cardapioList);
            model.addAttribute("restauranteAtual", restauranteAtual);
            return "mostrarItensRestaurante";
        }

        @GetMapping("/removerItemCardapio/{id}")
        public String removerItemCardapio(@PathVariable("id") int id) {
            ItemCardapio item = itemCardapioRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("O id do item do cardápio é inválido: " + id));
            itemCardapioRepository.delete(item);
            return "redirect:/mostrarItensRestaurante/"+item.getRestaurante().getId();
        }

        @GetMapping("/editarItemCardapio/{id}")
        public String exibirEditarItem(@PathVariable("id") int id, Model model) {
            ItemCardapio item = itemCardapioRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("O id do item do cardápio é inválido: " + id));
            model.addAttribute("item", item);
            return "editarItemCardapio";
        }

        @PostMapping("/editarItemCardapio/{id}")
        public String editarItem(@PathVariable("id") int id, @Valid @ModelAttribute("item") ItemCardapio item,
                                 BindingResult result, Model model) {
            if (result.hasErrors()) {
                item.setId(id);
                return "editarItemCardapio";
            }

            itemCardapioRepository.save(item);
            return "redirect:/mostrarItensRestaurante/" + item.getRestaurante().getId();
        }
    }
