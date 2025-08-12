# Restaurante

Implementação de um software que cadastra restaurantes e seus cardápios, e permite que usuários montem pedidos. 
Este projeto possui:
* Spring Web
* JPA
* Thymeleaf

## Aplicação Admin:
Neste projeto há uma aplicação para a **administração**, que vai permitir que o administrador: 
(i) Cadastre, liste, edite e apague restaurantes; 
(ii) Cadastre, liste, edite e apague itens de cardápio de um restaurante.

## Aplicação Consumidor:
Também há uma aplicação para o **consumidor**, que vai permitir que o consumidor 
(i) Liste restaurantes, consulte cardápios, e escolha itens para o seu pedido. 
O pedido fica gravado na sessão Web do usuário.

Todos estes dados são armazenados no banco de dados, cujo diagrama pode ser visualizado em diagrama-bd.png.
