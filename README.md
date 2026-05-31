# 🎓 MVC Web — Cadastro de Alunos

> Projeto didático em **Spring Boot** demonstrando o padrão arquitetural **MVC (Model-View-Controller)** com cadastro e listagem de alunos.

## 📚 O que é MVC?

**MVC (Model-View-Controller)** é um padrão arquitetural que divide a aplicação em três camadas com responsabilidades bem definidas, evitando que o código vire uma bagunça misturando lógica, dados e interface.

| Camada | Tradução | Responsabilidade |
|---|---|---|
| **Model** | Modelo | Representa os dados e as regras de negócio da aplicação |
| **View** | Visão | Exibe as informações para o usuário (HTML, telas) |
| **Controller** | Controlador | Recebe as ações do usuário e coordena Model e View |

**Por que usar MVC?**
- 🔀 **Separação de responsabilidades** — cada parte faz só o que é dela
- 🔧 **Manutenção mais fácil** — mudar a tela não afeta a lógica, e vice-versa
- 👥 **Trabalho em equipe** — diferentes pessoas podem trabalhar em camadas diferentes
- ♻️ **Reuso** — o mesmo Model pode ser usado por diferentes Views

**Como funciona na prática?**
O usuário interage com a **View** (clica num botão, preenche um formulário). Essa ação vai para o **Controller**, que processa a requisição, conversa com o **Model** (busca ou salva dados) e devolve o resultado para a **View** renderizar.

```
Usuário → View → Controller → Model
                     ↑            ↓
                    View ←────────┘
```

---

## 🏗️ Separação MVC — Arquitetura do Projeto

O projeto está organizado fisicamente de acordo com as camadas do padrão MVC:

```
mvc-web-alunos/
└── src/main/
    ├── java/com/exemplo/mvc/
    │   ├── model/
    │   │   └── Aluno.java              ← CAMADA MODEL
    │   ├── controller/
    │   │   └── AlunoController.java    ← CAMADA CONTROLLER
    │   └── MvcApplication.java         ← Ponto de entrada
    └── resources/
        └── templates/
            ├── alunos-form.html        ← CAMADA VIEW
            └── alunos-lista.html       ← CAMADA VIEW
```

---

## 🔍 O que cada camada faz

### 🟦 MODEL — `Aluno.java`
- Representa os **dados** da aplicação
- Contém a **regra de negócio**: nome não pode ser vazio
- **Não conhece** HTTP, HTML ou o Controller
- É independente — poderia ser usado em qualquer outro contexto

```java
public Aluno(String nome, String matricula) {
    if (nome == null || nome.isBlank()) {
        throw new IllegalArgumentException("Nome obrigatório"); // regra de negócio
    }
    ...
}
```

---

### 🟨 CONTROLLER — `AlunoController.java`
- **Coordena** a comunicação entre Model e View
- Recebe as requisições HTTP (`GET` e `POST`)
- Instancia o Model e passa dados para a View
- **Não valida regra de negócio** — delega ao Model
- **Não renderiza HTML** — delega à View

```java
@GetMapping("/alunos")
public String formulario() {
    return "alunos-form";           // direciona para a View
}

@PostMapping("/alunos")
public String cadastrar(..., Model model) {
    alunos.add(new Aluno(nome, matricula));   // usa o Model
    model.addAttribute("alunos", alunos);     // envia dados para a View
    return "alunos-lista";                    // direciona para a View
}
```

---

### 🟩 VIEW — `alunos-form.html` e `alunos-lista.html`
- **Só apresenta** os dados — não processa lógica
- Usa **Thymeleaf** para renderizar dados vindos do Controller
- `alunos-form.html` → formulário de cadastro
- `alunos-lista.html` → tabela com todos os alunos cadastrados

```html
<!-- Thymeleaf itera a lista enviada pelo Controller -->
<tr th:each="aluno : ${alunos}">
    <td th:text="${aluno.nome}"></td>
    <td th:text="${aluno.matricula}"></td>
</tr>
```

---

## 🔄 Fluxo completo de uma requisição

```
Navegador
   │
   ├─ GET /alunos
   │       └──→ Controller.formulario()
   │                   └──→ VIEW: alunos-form.html (exibe formulário)
   │
   └─ POST /alunos (nome + matrícula)
           └──→ Controller.cadastrar()
                       ├──→ MODEL: new Aluno(nome, matricula) (valida e cria)
                       ├──→ lista.add(aluno)
                       ├──→ model.addAttribute("alunos", lista)
                       └──→ VIEW: alunos-lista.html (exibe tabela)
```

---