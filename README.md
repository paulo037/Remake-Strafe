# e-Sports Testes
#### por: [Paula Gibrim] e [Paulo Henrique]


#### _Remake open-source do aplicativo Strafe E-sports_
Projeto desenvolvido como trabalho prático da disciplina CCF 313 - Programação Orientada a Objetos. O objetivo é colocar em prática e fixar todos os conceitos vistos em aula.

## Links importantes

 **[Strafe e-Sports]** - Projeto que inspirou o nosso trabalho.

 **[Java JDK]** - Linguagem usada para o desenvolvimento do trabalho


## Telas
### Perfil de usuário
O perfil de usuário é a aba onde o usuário encontra informações como: configurações, insigneas, jogos que o usuário curtiu e histórico de resultados das apostas. Nas configurações, é possível curtir novos jogos, configurar detalhes do app (como tema, notificações, seleção de país - para sites de notícias, idioma, envio de sugestões, etc.), e configurar informações pessoais (nome, usuário, e-mail, senha, etc.).


### Calendário
O calendário contém uma lista de todos as partidas que acontecerão, dos jogos curtidos, em todas as competições futuras com partidas já marcadas. Eles são exibidos separadamente por dia, e ordenados por horário do jogo. Ao selecionar uma partida específica, uma nova tela será exibida.


### Tela de partida
A tela de jogos possui três situações: antes da partida, durante a partida, e após a partida. Em todas as situações, a tela contém a imagem de ambos os times, a parte de apostas, a LineUp e os detalhes da competição. 

No caso de situações em que a partida ainda não foi iniciada, fica disponível o horário de início, as apostas estão abertas, e a LineUp é a esperada. Quando iniciada a partida, as apostas são encerradas, ao invés de horário passa a aparecer o placar atualizado em tempo real, bem como um gráfico mostrando a vitória de cada round; a LineUp passa a ser a que de fato compareceu (podendo ser diferente da esperada). Ao ser finalizada, o placar passa a ser o resultado da partida, bem como quem foi o vencedor (com destaque); o setor de apostas passa a mostrar a pontuação obtida com o palpite.


### Feed de notícias
O feed de notícias reúne notícias de sites de e-sports, tais como draft5.gg e maisesportes.com.br
Essas notícias estão associadas, principalmente, aos times, jogos, e jogadores curtidos.


## Conceitos importantes
### Curtir
Curtir um time, jogar ou jogo, significa colocá-lo em seus favoritos, de forma que todo o conteúdo do app será voltado para essas preferências

### Insígneas
Insigneas são tipos de troféis, entregue a jogadores com aquela faixa de pontuação nas apostas. 

| Título             |   Pontuação |
|:-------------------|------------:|
 | \<jogo> N00b       |       < 250 |
 | \<jogo> Bronze     |   250 - 500 |
 | \<jogo> Prata      |   501 - 750 |
 | \<jogo> Ouro       |  751 - 1000 |
 | \<jogo> Diamante   | 1001 - 1250 |
 | \<jogo> Desafiante | 1251 - 1500 |
 | \<jogo> Herói      |      1500 > |


**Nota:** Onde está escrito <jogo> deve ser substituído pelo nome do jogo da partida.



[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [Strafe e-Sports]: <https://www.strafe.com/>
   [Java JDK]:  <https://www.oracle.com/java/technologies/downloads/>
   [Paula Gibrim]: <emailto: paula.gibrim@ufv.br>
   [Paulo Henrique]: <emailto: paulo.h.carneiro@ufv.br>
