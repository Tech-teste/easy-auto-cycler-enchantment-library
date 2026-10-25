# Easy Auto Cycler Addon — Biblioteca de Encantamentos

## Português brasileiro

**Escolha os livros encantados que você quer encontrar, sem digitar os IDs dos encantamentos.** Este addon independente transforma a configuração de filtros do Easy Auto Cycler em uma biblioteca com busca, categorias de equipamento e detalhes de cada encantamento, para usar nas trocas do Easy Villagers.

A interface acompanha o idioma do Minecraft: **português brasileiro ou inglês**. O catálogo é montado a partir dos encantamentos disponíveis no mundo, incluindo os adicionados por outros mods quando marcados como negociáveis.

![Biblioteca 1.2.0 com favoritos, mod de origem e pagamento — PT-BR](https://media.forgecdn.net/attachments/2104/940/120-biblioteca-pt-br-png.png)

### O que você pode fazer

- Buscar pelo nome traduzido, pelo nome do mod ou pelo ID, sem precisar acertar os acentos.
- Ver o mod de origem no painel de detalhes e na dica do livro.
- Marcar favoritos com a estrela e mostrar apenas os favoritos.
- Salvar listas com nome, livros, níveis, preços, itens de pagamento e modo de busca.
- Revisar somente os livros marcados e limpar a seleção com confirmação.
- Filtrar por espada, picareta, armadura, élitro e outros equipamentos.
- Ativar e desativar livros, definir o preço máximo e usar o nível máximo padrão do encantamento.
- Consultar a descrição fornecida pelo mod que adiciona o encantamento.
- Escolher se basta encontrar qualquer filtro marcado ou se todos devem corresponder ao mesmo aldeão.
- Retomar a busca, a categoria, a página e o livro selecionado ao reabrir a biblioteca.
- Voltar à tela original do Auto Cycler para configurar filtros avançados.

![Listas salvas de livros — PT-BR](https://media.forgecdn.net/attachments/2104/942/120-listas-pt-br-png.png)

### Favoritos, listas e seleção

Clique na estrela ao lado de um livro para guardar ou remover um favorito. Use o botão **Todos os livros / Só favoritos** para alternar a visualização. O botão **Exibindo: todos / marcados** permite revisar sua seleção.

Em **Listas salvas**, dê um nome à seleção e clique em **Salvar lista**. Para reutilizar, selecione uma lista e clique em **Carregar lista**. Você pode guardar até 32 listas, com nomes de até 48 caracteres. Substituir uma lista pede confirmação. Os nomes são seus e permanecem iguais ao trocar o idioma.

Carregar uma lista muda somente a seleção em edição: **revise e clique em Salvar e aplicar** para atualizar os filtros ativos. Encantamentos que não existirem no mundo atual são ignorados e a quantidade é informada. As listas guardam os livros da biblioteca; filtros avançados não são incluídos nelas e continuam preservados.

Favoritos e listas ficam em `config/easy-auto-cycler-library.json`, separados dos filtros ativos, com uma cópia anterior `.bak`. Esse arquivo pode ser copiado para outro dos seus packs. Arquivos inválidos ou de formato desconhecido são preservados, sem sobrescrita automática. Cancelar a edição de filtros não desfaz favoritos ou listas que você já salvou.

O preço máximo mostra o item de pagamento configurado, normalmente esmeraldas. **Limpar marcados** pede confirmação e também só afeta os filtros ativos depois de salvar. Os filtros avançados permanecem inalterados.

![Somente livros favoritos — PT-BR](https://media.forgecdn.net/attachments/2104/944/120-favoritos-pt-br-png.png)

### Instalação e versões

**Requer NeoForge, Easy Auto Cycler e Easy Villagers.** Os dois mods estão cadastrados como dependências obrigatórias no CurseForge para que o aplicativo possa instalar as versões compatíveis. Escolha o arquivo da versão exata do seu Minecraft:

| Minecraft | Addon | Easy Auto Cycler | Ambiente de compilação |
| --- | --- | --- | --- |
| 1.21.1 | 1.2.0 Beta | 3.1.0 | NeoForge 21.1.248 / Java 21 |
| 26.1.2 | 1.2.0 Beta | 3.1.2 | NeoForge 26.1.2.121 / Java 25 |

Instale o addon e o Easy Auto Cycler **no cliente**. O Easy Villagers adiciona blocos e deve seguir os requisitos próprios de cliente e servidor. Este addon não precisa ser instalado no servidor. Ele configura os filtros; o Easy Auto Cycler executa a busca e o Easy Villagers fornece o bloco Trader.

1. Entre no mundo e abra a configuração habitual de filtros do Auto Cycler.
2. Procure os livros, marque os desejados e ajuste o preço máximo.
3. Clique em **Salvar e aplicar** e use o controle normal do Auto Cycler para começar a busca.

**Cancelar** ou **Esc** descarta edições não salvas. Um backup dos filtros é criado antes de salvar. Remover o addon restaura a tela original, mantendo os filtros no formato do Auto Cycler.

![Biblioteca 1.2.0 no Minecraft 1.21.1 — PT-BR](https://media.forgecdn.net/attachments/2104/946/120-minecraft-1211-pt-br-png.png)

### Modpacks, limites e feedback

Pode ser incluído em modpacks sob a licença MIT. Não exige ATM10 nem uma lista exportada de filtros: a biblioteca se adapta ao mundo atual. As imagens são capturas reais da 1.2.0 no Minecraft 26.1.2, em um mundo de teste separado do FTB Evolution 2, e em uma instalação de teste separada do Minecraft 1.21.1.

Maldições, IDs que contêm `curse` e encantamentos fora da tag `minecraft:tradeable` ficam fora da lista. Níveis extras de sistemas como Apothic não são usados como máximo padrão. Uma entrada no catálogo não garante que as regras do modpack permitam a oferta. Descrições dependem dos mods instalados; alguns equipamentos de mods aparecem em **Outros**. Não há importação direta do JEI. Filtros avançados de itens e duplicados válidos são preservados; ao salvar, filtros de encantamentos ausentes, não negociáveis ou excluídos como maldição são removidos, com backup prévio.

**Versão Beta:** compilação, integração, traduções e persistência de listas/favoritos verificadas nos dois Minecraft. As duas interfaces foram conferidas dentro do jogo em PT-BR e inglês, em mundos de teste separados. Também foram testados o carregamento de listas entre catálogos diferentes, a preservação do pagamento e modo, cancelar, limpar com confirmação e salvar os filtros. Outras versões de Minecraft, loaders e Auto Cycler não são suportadas por estes arquivos.

Comentários estão abertos. Para relatar um problema, informe Minecraft, NeoForge, versão do addon e dos dois mods, além dos passos para reproduzir. [Código-fonte e instruções de compilação](https://github.com/Tech-teste/easy-auto-cycler-enchantment-library) · [Relatar um problema](https://github.com/Tech-teste/easy-auto-cycler-enchantment-library/issues)

### Projeto pessoal, autoria e créditos

Este addon foi criado para meu uso próprio nos meus modpacks. **Todo o código, as adaptações entre versões e as traduções do addon foram feitos pelo Codex, da OpenAI, seguindo meus pedidos. Eu não sou desenvolvedor de mods nem especialista no assunto.** Estou compartilhando o resultado porque pode ser útil para outras pessoas. É um projeto experimental, compartilhado como está, que pode conter bugs e incompatibilidades.

Essa informação se refere somente a este addon. **Easy Auto Cycler é de [Uncraftbar](https://www.curseforge.com/minecraft/mc-mods/easy-auto-cycler)** e **Easy Villagers é de [henkelmax](https://www.curseforge.com/minecraft/mc-mods/easy-villagers)**. Os mods originais são distribuídos separadamente. Este é um addon independente e não oficial, sem afiliação com seus autores. A logo ilustrativa foi gerada com IA; as imagens da interface são capturas reais do jogo.

---

## English

**Choose the enchanted books you want to find without typing enchantment IDs.** This independent addon turns Easy Auto Cycler's filter configuration into a searchable library with equipment categories and enchantment details, for the Easy Villagers trading workflow.

The interface follows your Minecraft language: **English or Brazilian Portuguese**. The catalog reads the current world's enchantments, including modded enchantments when tagged as tradeable.

![Library 1.2.0 with favorites, source mod and payment — English](https://media.forgecdn.net/attachments/2104/941/120-library-english-png.png)

### Features

- Search by translated name, source mod name or ID, with accent-insensitive matching.
- See the source mod in the detail panel and book tooltip.
- Star favorites and switch to a favorites-only view.
- Save named book lists with levels, prices, payment items and match mode.
- Review selected books and clear the library selection with confirmation.
- Filter by sword, pickaxe, armor, elytra and other equipment.
- Enable or disable book filters, set the maximum price and use the enchantment's standard maximum level.
- Read descriptions supplied by the mod that adds each enchantment.
- Match any selected filter or require all selected filters on the same villager.
- Remember your search, category, page and selected enchantment between sessions.
- Open the original Auto Cycler screen for advanced filters.

![Saved book lists — English](https://media.forgecdn.net/attachments/2104/943/120-saved-lists-english-png.png)

### Favorites, saved lists and selection

Click a book's star to add or remove a favorite. Use **All books / Favorites only** to switch views, and **Showing: all / selected** to review your selection.

In **Saved lists**, name your selection and click **Save list**. To reuse it, select a list and click **Load list**. You can store up to 32 lists with names up to 48 characters. Replacing an existing list requires confirmation. Names are user-defined and remain unchanged when switching languages.

Loading a list changes the draft only: **review it and click Save and apply** to update active filters. Enchantments unavailable in the current world are skipped and counted. Lists store library books only; advanced filters are not included and remain preserved.

Favorites and lists are stored in `config/easy-auto-cycler-library.json`, separately from active trade filters, with a previous `.bak` copy. You can copy this file to another of your packs. Invalid or unknown-format files are preserved instead of silently overwritten. Canceling trade-filter edits does not undo favorites or lists already saved.

The price limit identifies the configured payment item, usually emeralds. **Clear selected** requires confirmation and only updates active filters after saving. Advanced filters remain unchanged.

![Review selected books — English](https://media.forgecdn.net/attachments/2104/945/120-selected-english-png.png)

### Installation and versions

**Requires NeoForge, Easy Auto Cycler and Easy Villagers.** Both mods are listed as required CurseForge dependencies so the app can install matching versions. Download the file for your exact Minecraft version:

| Minecraft | Addon | Easy Auto Cycler | Build environment |
| --- | --- | --- | --- |
| 1.21.1 | 1.2.0 Beta | 3.1.0 | NeoForge 21.1.248 / Java 21 |
| 26.1.2 | 1.2.0 Beta | 3.1.2 | NeoForge 26.1.2.121 / Java 25 |

Install this addon and Easy Auto Cycler **on the client**. Easy Villagers adds content and must follow its own client/server requirements. The addon does not need to be installed on the server. It configures filters; Easy Auto Cycler runs the search and Easy Villagers supplies the Trader block.

1. Join a world and open the usual Auto Cycler filter settings.
2. Search for books, select the ones you want and set the maximum price.
3. Click **Save and apply**, then start the search using Auto Cycler's normal controls.

**Cancel** or **Esc** discards unsaved edits. The existing filters are backed up before saving. Removing the addon restores the original screen; saved filters remain in Auto Cycler's format.

![Library 1.2.0 on Minecraft 1.21.1 — English](https://media.forgecdn.net/attachments/2104/947/120-minecraft-1211-english-png.png)

### Modpacks, limitations and feedback

Modpacks may include the addon under the MIT license. It does not require ATM10 or an exported filter list: the library adapts to the current world. These are actual 1.2.0 captures from a separate FTB Evolution 2 test world on Minecraft 26.1.2 and a separate Minecraft 1.21.1 test installation.

Curses, IDs containing `curse`, and enchantments outside `minecraft:tradeable` are excluded. Extra levels from systems such as Apothic are not used as the standard maximum. A catalog entry does not guarantee that a pack's trading rules allow an offer. Descriptions depend on installed mods; some mod-specific equipment appears under **Others**. Direct JEI import is not included. Advanced item filters and valid duplicates are preserved; saving removes enchantment filters that are absent, non-tradeable or excluded as curses, after creating a backup.

**Beta:** build, integration, localization and favorites/list persistence checks passed for both Minecraft versions. Both interfaces were visually checked in separate test worlds in English and PT-BR. Cross-catalog list loading, payment/mode restoration, Cancel isolation, confirmed clear and real filter saving were also tested. These files do not support other Minecraft versions, loaders or Auto Cycler versions.

Comments are open. When reporting a bug, include Minecraft, NeoForge, addon and dependency versions, plus reproduction steps. [Source code and build instructions](https://github.com/Tech-teste/easy-auto-cycler-enchantment-library) · [Report an issue](https://github.com/Tech-teste/easy-auto-cycler-enchantment-library/issues)

### Personal project, authorship and credits

This addon was created for my own use in my modpacks. **All addon code, version adaptations and translations were produced by OpenAI Codex, following my requests. I am not a mod developer or a modding expert.** I am sharing the result because it may be useful to others. It is an experimental project, shared as is, and may contain bugs or compatibility issues.

This disclosure applies only to this addon. **Easy Auto Cycler is by [Uncraftbar](https://www.curseforge.com/minecraft/mc-mods/easy-auto-cycler)** and **Easy Villagers is by [henkelmax](https://www.curseforge.com/minecraft/mc-mods/easy-villagers)**. The original mods are distributed separately. This is an independent, unofficial addon with no affiliation to their authors. The illustrative logo was AI-generated; interface images are actual in-game captures.
