# Documentação Projeto

# Introdução

O projeto escolhido foi o desenvolvimento de um aplicativo *mobile* semelhante ao já existente *Strafe e-Sports*, utilizado para acompanhar o cenário competitivo dos jogos eletrônicos, além de oferecer a opção de apostas. A linguagem de programação utilizada foi Java, nos permitindo aplicar os conhecimentos vistos em sala de aula. Diante disso, o trabalho conta com implementações de testes, encapsulamento e modularidade, além de tratamento de exceção e polimorfismo, os quais serão melhor discutidos adiante.

Para a realização do projeto, foi utilizado principalmente os IDEs Android Studio, IntelliJ IDEA e Visual Studio Code. É importante destacar que houveram reuniões frequentes, tendo sido fundamental o uso da extensão *Live Share*, para que ambos os integrantes pudessem fazer alterações nos códigos em tempo real.

# Conceitos importantes

Abaixo falaremos sobre alguns conceitos que são importantes para o entendimento do funcionamento do aplicativo.

**Favoritos:** os jogos incluídos na lista de favoritos serão os que são acompanhados pelo usuário, de forma que as partidas do referente jogo aparecerão na tela de partidas.

**Curtir:** curtir um jogo significa que o referente passará a fazer parte da lista de favoritos.

**Apostar:** na tela de calendario e na tela de jogar, para cada jogo, existe a opção de fazer uma aposta em qual será o time vencedor; para apostar, basta clicar na opção referente ao time escolhido e selecionar quantos pontos serão apostados.

**Ranking:** ao fazer apostas, existe uma movimentação de pontos (você ganha ou perde); esses pontos são usados para estabelecer o ranking do usuário, de acordo com a seguinte tabela:

| Título | Pontuação |
| --- | --- |
| N00b | < 250 |
| Bronze | 250 - 500 |
| Prata | 501 - 750 |
| Ouro | 751 - 1000 |
| Diamante | 1001 - 1250 |
| Desafiante | 1251 - 1500 |
| Herói | > 1501 |

# Desenvolvimento

Visando uma melhor receptividade do usuário, optamos por fazer um aplicativo mobile, com interface bem definida. Tal interface foi toda construída usando as ferramentas do Android Studio.

## Organização

### Gerenciador de Bibliotecas

Devido ao fato do desenvolvimento do aplicativo ser feito voltado para o sistema Android, temos  como gerenciadores de biblioteca e de dependências  o ***Gradle*** e o ***Maven***.

- ***Gradle:***  é um sistema avançado de automatização de builds que une o melhor da flexibilidade do Ant com o gerenciamento de dependências e as convenções do Maven. Os arquivos de build do *Gradle* são scripts escritos na linguagem *Groovy*, ao contrário dos formatos de construção do *Ant* e *Maven* que usam arquivos XML para a configuração. Por serem baseados em scripts, os arquivos do *Gradle* permitem que você realize tarefas de programação em seu arquivo de configuração. O *Gradle* ainda conta com um sistema de plugins que adicionam funcionalidades extras ao seu core.
- **Maven**: Em seu cerne, o *Maven* é uma ferramenta de gerenciamento e automação de construção (build) de projetos. Entretanto, por fornecer diversas funcionalidades adicionais através do uso de plugins e estimular o emprego de melhores práticas de organização, desenvolvimento e manutenção de projetos, é muito mais do que apenas uma ferramenta auxiliar.

### Padrão MVC

Para a organização do App em camadas foi utilizado o MVC  (Model-View-Controller) que é um padrão em design de software comumente usado para implementar interfaces de usuário, dados e lógica de controle. Ele enfatiza uma separação entre a lógica de negócios do software e a exibição. Esta "separação de interesses" proporciona uma melhor divisão do trabalho e uma melhor manutenção. Para a implementação do MVC no presente projeto foi utilizado ainda uma camada extra, a camada DAO que tem a função de conectar e fazer requisições para o banco  de dados.

 

## Android

Para o desenvolvimento do projeto foi escolhido o sistema Android , assim nos é disponibilizado  ferramentas para a criação do aplicativo, uma das ferramentas o Android Studio que foi a IDE principal no desenvolvimento do projeto.  Para a criação do App Android temos algumas classes essências que são herdadas durante o Desenvolvimento do projeto:  Activity, Fragment, etc...

### Activity

Uma Activity é o ponto de entrada para a interação com o usuário. Ela representa uma tela única com uma interface do usuário. Por exemplo, o  app  contem uma Activity  que mostra uma tela de Login outra uma tela de cadastro. Embora essas Activitys funcionem juntas para formar uma experiência do usuário coesa no app , elas são independentes entre si. Portanto, o aplicativo diferente pode iniciar qualquer uma dessas atividades. Devido isso nosso app pode abrir o aplicativo da gakeria para que seja selecionado uma foto de perfil.

- Acompanhamento do que interessa ao usuário atualmente (o que está na tela) para garantir que o sistema permaneça executando processos que hospedam a atividade.
- Conhecimento dos processos usados anteriormente que contêm coisas a que o usuário pode retornar (atividades interrompidas) e, portanto, priorização da manutenção desses processos.
- Auxílio ao aplicativo quanto aos processos interrompidos para que o usuário possa retornar às atividades com o estado anterior restaurado.
- Oferecimento de uma maneira de os aplicativos implementarem os fluxos de usuários entre si e o sistema coordenar esses fluxos.

#### CadastrarActivity

 <p align="center"><img src="https://user-images.githubusercontent.com/80071894/160035528-d21aa9a5-af71-49b6-a8dc-58abe2fd206a.png" height="500px"  alt=""></p>

  Responsável por receber os dados de cadastro de um novo usuário no sistema.

#### ConfiguracoesActivity

 <p align="center"><img src="https://user-images.githubusercontent.com/80071894/160036218-a60e2d0d-f0b2-4c50-a6cd-ff661f664531.png" height="500px"  alt=""></p>

Responsavel por receber do usuario através de check-boxs os jogos que o usuario deseja acompanhar.

#### LoginActivity

<p align="center"><img src="https://user-images.githubusercontent.com/80071894/160036371-d69e4636-f101-4be3-8a57-02a83d4bbb70.png" height="500px"  alt=""></p>


Responsável por receber os dados de de login de um usuário.

#### MainActivity

<p align="center"><img src="https://user-images.githubusercontent.com/80071894/160036437-0775f17f-91c7-4ab5-bb32-4fd22eda6384.png" height="500px"  alt=""></p>


Diferentemente das demais Activitys a MainActivity contém apenas uma barra de navegação fixa, mas contém um container de fragmentos, isso se é devido a mudança de conteudos na tela através de diversos fragmentos que podem ser inflados dentro da activity, os principais fragmentos dentro do aplicativo são Calendario, Feed, Video, Jogar e Perfil.

### Fragmentos

Os fragmentos introduzem a modularidade e a reutilização na IU nas Activitys, permitindo dividie a IU em blocos discretos. As Activitys são um local ideal para colocar elementos globais em torno da interface do usuário do  app, como uma gaveta de navegação. Por outro lado, os fragmentos são mais adequados para definir e gerenciar a IU de uma única tela ou parte de uma tela.

Assim separar os elementos de navegação do conteúdo tornar esse processo mais gerenciável. A Activity é responsável por exibir a IU de navegação correta enquanto o fragmento exibe a lista com o layout adequado.

CalendarioFragment

- Foto

  Responsável por exibir as partidas separadas por dia e filtradas para mostrar apenas as partidas dos jogos curtidos.

FeedFragment

- Foto

 Responsável por exibir as noticias dos jogos curtidos, ordenadas da mais nova para mais antiga.

VideoFragment

- Foto

Responsável por exibir os videos  dos jogos curtidos, ordenadas do mais novo para mais antigo.

### Fluxo do aplicativo

- Link para o site com o fluxo

## Fire Base

O Firestore  é um banco de dados não relacional pertencente ao Fire Base e foi  escolhido para se armazenar os dados do Projeto. O Fire Base ainda conta com a  ferramenta de autenticação (Fire Base Authentication) que foi utilizada para realizar a autenticação do usuario a parte de login e cadastro, a autenticação é realizada com o email do usuário. 

## Regras de Negocio

### Partida

Uma partida é apresentada no App se está no intervalo de  15 dias para frente ou 15 dias para atrás tendo como ponto a data atual e sendo as partidas agrupadas por Dia no  fragment calendario e por jogo no fragment jogar, que diferente do calendário mostra apenas partidas que não se encerraram. Se uma partida ainda não terminou é permitido realizar apostas em cima do resultado da mesma, do contrário caso a partida tenha encerrado mais esteja em um intervalo de 15 dias ela ainda é visível no fragmento calendario com o resuldado da partida. 

### Aposta

Uma aposta pode ser feita em uma partida ainda não terminada. Feita a aposta é subtraído o valor do saldo do usuário. Ao encerrar da partida é verificado o resultado, caso o vencedor seja o mesmo time da aposta  feita, é gerada uma recompensa no valor da aposta multiplicado pelo multiplicador contido na hora da aposta. a recompensa pode ser coletada no FragmentJogar, ao coletar é somado o valor da recompensa ao saldo do usuário e somado  1 no número de acertos. Caso o time vencedor seja diferente do da aposta é somado  1 no número de erros.

### Video

Os videos são mostrados no FragmentVideo se o conteudo do video for relacionado a um dos jogos curtidos pelo usuário, ao clicar em cima de um video o mesmo redireciona o usuário ao youtube com o conteúdo do presente video.

### Noticia

As notícias são mostrados no FragmentFeed se o conteudo da noticia for relacionado a um dos jogos curtidos pelo usuário, ao clicar em uma notíca é aberto uma previsualização do conteúdo da notícia, caso o usuário deseje continuar lendo a notícia, basta clicar no link ao final da notícia para ser redirecionado para o site contendo a matéria completa.

## Como Instalar o App

O App pode ser instalado em celulares com o sistema Android para a instalação do App basta realizar o download do .apk contido em **Link para o apk**. 

Caso tenha dúvidas em relação a instalação de um App fora da Google Play Store assistir o tutorial abaixo:

**Link para o tutorial**

# Conclusão

Durante todo o processo de desenvolvimento, tivemos a oportunidade de colocar em prática os conceitos aprendidos em sala de aula, e ver na prática como aplicá-los. Houveram algumas dificuldades para aplicação deles devido ao tamanho do projeto, mas conseguimos resolver e tirar proveito do padrão MVC para facilitar na resolução dos mesmo . Algumas funcionalidades previamente definidas tiveram que ser removidas do projeto, uma vez que dependiam de um conhecimento mais avançado e um tempo maior para elaboração.

Além disso, houve um problema com a sincronização de um dos integrantes no GitHub, o que fez com que o trabalho fosse desenvolvido quase todo fazendo uso da extensão *LiveShare*. De início foi um processo bem desafiador, mas conseguimos nos adaptar bem à situação.

Diante disso, conseguimos obter um projeto bonito e bem estruturado, que possui a maior parte das funcionalidades pretendidas. Esse trabalho permitiu uma maior fluência na linguagem Java, bem como maior domínio de algumas habilidades no desenvolvimento de aplicações Android e tambem o aprendizado do Fire Base e suas funcionalidades.

# Referências

https://www.devmedia.com.br/padrao-mvc-java-magazine/21995

[https://www.devmedia.com.br/introducao-ao-maven/25128#1](https://www.devmedia.com.br/introducao-ao-maven/25128#1)

[https://developer.android.com](https://developer.android.com/guide/fragments)

[https://www.devmedia.com.br/entendendo-e-aplicando-heranca-em-java/24544](https://www.devmedia.com.br/entendendo-e-aplicando-heranca-em-java/24544)

[https://www.youtube.com/c/TiagoAguiar](https://www.youtube.com/c/TiagoAguiar)
