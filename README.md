# HexagonApp
 Relatório do Projeto HexagonApp
 # Descrição Geral
 O HexagonApp é um aplicativo Android desenvolvido para gerenciar um usuário, permitindo a
 inserção, edição, visualização e ativação/desativação de um usuário. O projeto utiliza a arquitetura
 MVVM (Model-View-ViewModel) e é construído com Jetpack Compose para a interface do usuário.
 A injeção de dependência é gerenciada pelo Dagger Hilt, e o armazenamento de dado é feito com
 Room.
 Decisões de Desenvolvimento
 1. Arquitetura MVVM: Escolheu-se a arquitetura MVVM para separar claramente a lógica de
 negócio da interface do usuário, facilitando a manutenção e teste.
 2. Jetpack Compose: Utilizou-se Jetpack Compose para a construção da interface do usuário
 devido à sua moderna abordagem declarativa, que simplifica a criação de interface complexa.
 3. Dagger Hilt: Optou-se pelo Dagger Hilt para a injeção de dependência, simplificando a
 configuração e gerenciamento de dependência no projeto.
 4. Room: Utilizou-se Room para o armazenamento local de dado, aproveitando suas vantagens
 como verificação de consulta em tempo de compilação e integração com coroutines e LiveData.

 # Biblioteca Utilizada
    - Jetpack Compose: Para a construção da interface do usuário.
    - Dagger Hilt: Para a injeção de dependência.
    - Room: Para o armazenamento local de dado.
    - Coil: Para o carregamento de imagem.
    - Coroutines: Para operação assíncrona.
    - Navigation Compose: Para a navegação entre tela.
    - JUnit e Mockito: Para teste unitário e de UI.
       
 # Funcionalidade Implementada
 1. Inserção de Usuário
    - Tela InsertUserScreen permite ao usuário inserir novo usuário com informação como nome, CPF,
 cidade, data de nascimento e foto.
    - Utiliza ActivityResultContracts.GetContent para selecionar imagem da galeria.
    - Verificação de permissão para acessar a galeria de imagem.
 2. Edição de Usuário
    - Tela EditUserScreen permite editar a informação de um usuário existente.
    - Similar à tela de inserção, mas pré-preenche o campo com os dado do usuário selecionado.
 3. Visualização de Usuário Ativo e Inativo
    - Tela MainScreen exibe a lista de usuário ativo.
    - Tela InactiveUsersScreen exibe a lista de usuário inativo.
    - Ambas as telas utilizam LazyColumn para exibir lista de usuário e permitem alternar o status de
 atividade do usuário.
 4. Navegação
    - Utiliza NavHost e NavController para gerenciar a navegação entre as telas.
    - Navegação configurada para permitir a transição entre a tela principal, inserção, edição e
      visualização de usuário inativo.
 5. Injeção de Dependência
    - Configuração do Dagger Hilt para fornecer instância de UserDao e AppDatabase.
    - Anotação @HiltViewModel para ViewModel, permitindo a injeção de dependência.
 6. Armazenamento de Dado
    - Configuração do Room com a entidade User e o DAO UserDao.
    - Utilização de coroutines para operação de banco de dado assíncrona.
