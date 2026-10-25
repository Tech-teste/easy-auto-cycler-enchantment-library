# Easy Auto Cycler Addon — Enchantment Library

Biblioteca de Encantamentos · **1.2.0 Beta** · PT-BR / English · NeoForge · MIT

![Biblioteca 1.2.0](https://media.forgecdn.net/attachments/2104/940/120-biblioteca-pt-br-png.png)

## Português brasileiro

Addon independente para configurar os livros que o Easy Auto Cycler procura nas trocas do Easy Villagers. Inclui busca por nome, mod ou ID, categorias de equipamento, mod de origem, favoritos por estrela, listas salvas e revisão dos livros marcados. Interface em português brasileiro ou inglês conforme o idioma do Minecraft.

[CurseForge](https://www.curseforge.com/minecraft/mc-mods/easy-auto-cycler-biblioteca-de-encantamentos) · [Descrição completa e imagens](DESCRIPTION.md) · [Mudanças da 1.2.0](CHANGELOG-1.2.0.md) · [Relatar problema](https://github.com/Tech-teste/easy-auto-cycler-enchantment-library/issues)

### Instalar e usar

Requer **NeoForge, Easy Auto Cycler e Easy Villagers**, com as versões compatíveis abaixo. O addon é instalado somente no cliente; Easy Villagers segue seus próprios requisitos de cliente/servidor. Abra os filtros habituais do Auto Cycler, escolha os livros e clique em **Salvar e aplicar**. Depois use o controle normal do Auto Cycler para começar a busca.

As estrelas guardam favoritos. Em Listas salvas, você pode guardar até 32 seleções com nome, níveis, preços, itens de pagamento e modo de busca. Carregar uma lista muda apenas a seleção em edição, ignora encantamentos indisponíveis e informa a quantidade: revise antes de aplicar. Limpar marcados pede confirmação; filtros avançados permanecem intactos. Cancelar descarta as edições de filtros, mas mantém favoritos e listas que você já salvou.

Favoritos e listas ficam em `config/easy-auto-cycler-library.json`, com uma cópia anterior `.bak`. O arquivo pode ser copiado para outros packs. Filtros e preferências das versões anteriores continuam compatíveis. Arquivos SOURCE e ZIPs 1.0.9 foram preservados como histórico; o código atual está nas pastas abaixo.

### Código-fonte e compilação

| Pasta | Minecraft | Java | NeoForge de compilação | Easy Auto Cycler |
| --- | --- | --- | --- | --- |
| [minecraft-1.21.1](minecraft-1.21.1) | 1.21.1 | 21 | 21.1.248 | 3.1.0 |
| [minecraft-26.1.2](minecraft-26.1.2) | 26.1.2 | 25 | 26.1.2.121 | 3.1.2 |

1. Clone este repositório ou baixe os fontes. Selecione a pasta da sua versão e configure o JDK indicado em JAVA_HOME.
2. Baixe o jar original Easy Auto Cycler, da versão indicada e NeoForge, e coloque em `libs/`. O nome exato está em `libs/README.md`. Os mods originais não são redistribuídos aqui.
3. Execute `gradlew.bat build` no Windows. No Linux, execute `chmod +x gradlew` e `./gradlew build`.
4. O jar gerado fica em `build/libs/`. A compilação executa os testes de integração, idiomas e persistência da biblioteca.

### Testes e origem

Os dois builds passaram nos testes de integração e traduções. A 1.21.1 foi conferida em uma instalação de teste separada; a 26.1.2, em um mundo de teste separado do FTB Evolution 2. Foram verificados PT-BR/inglês, carregar listas com encantamentos ausentes, preservar pagamento e modo, cancelar sem alterar filtros ativos, limpar com confirmação e salvar. Capturas da interface são reais; a logo ilustrativa foi gerada com IA.

Projeto criado para uso próprio em modpacks. **Todo o código, ports e traduções foram produzidos pelo Codex, da OpenAI, seguindo os pedidos do proprietário, que não é desenvolvedor de mods nem especialista.** Veja [NOTICE.md](NOTICE.md). É um projeto experimental, compartilhado como está. O identificador interno legado foi mantido para preservar as configurações.

## English

Independent client addon for choosing enchanted books in Easy Auto Cycler filters, for the Easy Villagers workflow. Features searchable equipment categories, source mod labels, starred favorites, named reusable lists, selected-book review, confirmed clear, explicit payment limits and a clearer Save and apply action. The interface follows Minecraft's language setting: English or Brazilian Portuguese.

Requires matching NeoForge, Easy Auto Cycler and Easy Villagers. Favorites and up to 32 named lists are stored separately in `config/easy-auto-cycler-library.json`, with a previous `.bak` copy. Lists store levels, prices, payment items and match mode. Loading edits the draft only and reports unavailable enchantments; review before clicking Save and apply. Advanced filters remain preserved. Cancel discards draft filter edits while keeping already-saved favorites and lists.

Source projects are directly browsable in the two version folders above. Install the JDK from the table, put the separately downloaded original Easy Auto Cycler jar in that project's libs directory, and run the Gradle wrapper build task. Output is in build/libs/. Integration, localization and library-persistence checks run during build. Original dependency binaries are not included. Legacy SOURCE documents and 1.0.9 archives are preserved as history.

Both Minecraft interfaces were checked in separate test worlds in English and PT-BR, including list loading, missing IDs, payment/mode restore, Cancel isolation, confirmed clear and actual filter saving. This personal experimental project was built entirely through OpenAI Codex at the owner's request; the owner is not a mod developer or modding expert. See NOTICE.md. Screenshots are real; the logo was AI-generated.

## Credits

- [Easy Auto Cycler](https://www.curseforge.com/minecraft/mc-mods/easy-auto-cycler) by Uncraftbar.
- [Easy Villagers](https://www.curseforge.com/minecraft/mc-mods/easy-villagers) by henkelmax.
- This unofficial addon is independent of both projects. The MIT license covers this addon's code, not the original mods.
