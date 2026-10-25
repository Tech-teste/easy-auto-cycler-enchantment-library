# Easy Auto Cycler Addon — Enchantment Library

Biblioteca de Encantamentos · PT-BR / English · NeoForge · MIT

![PT-BR interface](https://media.forgecdn.net/attachments/2104/568/01-biblioteca-pt-br-png.png)

## Português brasileiro

Addon independente para escolher livros encantados nos filtros do Easy Auto Cycler, com busca, categorias de equipamento e descrições. Requer **Easy Auto Cycler e Easy Villagers**. Não adiciona blocos, ofertas ou encantamentos. Instalação do addon somente no cliente.

[Baixar no CurseForge](https://www.curseforge.com/minecraft/mc-mods/easy-auto-cycler-biblioteca-de-encantamentos) · [Descrição completa e imagens](DESCRIPTION.md) · [Problemas e sugestões](https://github.com/Tech-teste/easy-auto-cycler-enchantment-library/issues)

### Código-fonte completo

- [Minecraft 1.21.1 — baixar fontes e Gradle wrapper](easy-auto-cycler-addon-1.21.1-1.0.9-sources.zip) · [Conferir o código no navegador](SOURCE-1.21.1.md)
- [Minecraft 26.1.2 — baixar fontes e Gradle wrapper](easy-auto-cycler-addon-26.1.2-1.0.9-sources.zip) · [Conferir o código no navegador](SOURCE-26.1.2.md)

Os ZIPs preservam a estrutura completa dos projetos. Os documentos SOURCE são cópias para leitura no navegador, incluindo Java, configurações e traduções. Não incluem os binários dos mods originais, dados de conta ou mundos.

### Compilar

| Pasta | Minecraft | Java | NeoForge | Easy Auto Cycler |
| --- | --- | --- | --- | --- |
| minecraft-1.21.1 | 1.21.1 | 21 | 21.1.248 | 3.1.0 |
| minecraft-26.1.2 | 26.1.2 | 25 | 26.1.2.121 | 3.1.2 |

1. Baixe e extraia o ZIP de fontes da versão escolhida; instale o JDK correspondente e configure JAVA_HOME.
2. Baixe o jar original do [Easy Auto Cycler](https://www.curseforge.com/minecraft/mc-mods/easy-auto-cycler), para a mesma versão e NeoForge, e coloque em libs/ dentro da pasta correspondente. Consulte libs/README.md para o nome exato. Os mods originais não são redistribuídos aqui.
3. Nessa pasta, execute `./gradlew build` (Windows: `gradlew.bat build`). No Linux, use `chmod +x gradlew` antes.
4. O jar está em build/libs/. Os testes de integração e traduções são executados durante o build.

### Origem e estado

Projeto criado para uso próprio em modpacks. Todo o código, ports e traduções foram produzidos pelo Codex, da OpenAI, seguindo os pedidos do proprietário, que não é desenvolvedor de mods nem especialista. Veja [NOTICE.md](NOTICE.md). Projeto experimental, compartilhado como está, sem promessa de suporte profissional.

1.0.9 atualiza nome público, logo e dependência explícita de Easy Villagers. O código das telas é o mesmo da versão anterior: a 26.1.2 foi conferida em PT-BR e inglês em mundo de teste separado do FTB Evolution 2; uma nova conferência visual da 1.21.1 está pendente. Os prints são reais. A logo foi gerada com IA. O identificador interno legado é mantido para compatibilidade com as configurações existentes.

## English

Independent addon for selecting enchanted books in Easy Auto Cycler filters, with search, equipment categories and enchantment descriptions. Requires **Easy Auto Cycler and Easy Villagers**. Adds no blocks, offers or enchantments. Install the addon on the client only.

Complete buildable projects are in the two sources ZIPs above. SOURCE documents provide browser-readable copies of all Java, metadata, build files and translations. To build, download and extract the matching source ZIP, install its JDK, set JAVA_HOME, download the matching original Easy Auto Cycler NeoForge jar into that folder's libs/ directory, and run `./gradlew build` (`gradlew.bat build` on Windows). See libs/README.md for the exact filename. Output is in build/libs/. Integration and language checks run during build. Original dependency binaries are not included.

This is a personal, experimental modpack project. All addon code, ports and translations were produced by OpenAI Codex at the owner's request. The owner is not a mod developer or modding expert. See [NOTICE.md](NOTICE.md). Version 1.0.9 changes branding and dependency metadata; screen code is unchanged from the previous build. Minecraft 26.1.2 was visually checked in a separate FTB Evolution 2 test world in both languages; a fresh 1.21.1 visual check is pending. Screenshots are real; the logo was AI-generated.

## Credits

- [Easy Auto Cycler](https://www.curseforge.com/minecraft/mc-mods/easy-auto-cycler) by Uncraftbar.
- [Easy Villagers](https://www.curseforge.com/minecraft/mc-mods/easy-villagers) by henkelmax.
- This unofficial addon is independent of both projects and their authors. The MIT license covers this addon's code, not the original mods.
