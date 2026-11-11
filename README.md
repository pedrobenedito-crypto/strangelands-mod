# Strangelands (Minecraft 1.20.1 · Forge)

Uma dimensão nova, parecida com o The End (ilhas flutuantes sobre o vazio e céu do End), mas com **blocos de grama**
e uma vida diferente do habitual: árvores de tronco cinza com copa rosa, flores que brilham, grama de cor turquesa
e os **Esporinhos** (criaturas roxas que flutuam devagar ao cair).

## Como chegar na dimensão
1. Crie o **Balde de Líquido Estranho** (receita sem forma): balde de água + fragmento de ametista + fruta de chorus.
2. Esvazie o balde no chão (clique direito), como um balde de água.
3. **Entre no líquido**: você é teleportado para a dimensão (`strangelands:strange_realm`).
4. Na ilha onde você chega existe uma poça do mesmo líquido: **entre nela para voltar** ao ponto de onde saiu
   (se o ponto de origem não for conhecido, volta para o spawn do mundo).
- Se cair no vazio da dimensão, você é levado de volta para a ilha inicial em vez de morrer.
- Para testar direto: `/execute in strangelands:strange_realm run tp @s 0 66 0`
- Itens de criativo: balde, Flor Brilhante e ovo de spawn do Esporinho.

## Build online no GitHub
1. Crie um repositório novo e envie todo o conteúdo desta pasta (incluindo `.github`).
2. Aba **Actions** → **Build mod** (roda a cada push ou em *Run workflow*).
3. Baixe o artifact **strangelands-mod**: dentro está o `.jar`. Coloque na pasta `mods` (Forge 1.20.1, 47.x).

Usa Java 17 + Gradle 8.1.1 (instalado pelo Actions, sem gradle wrapper).

## Onde mexer
- `world/StrangeTeleporter` – lógica de ida e volta, ilha inicial, resgate do vazio
- `block/StrangeLiquidBlock` – o líquido que teleporta ao pisar
- `data/strangelands/worldgen/noise_settings/strange_realm.json` – formato das ilhas (densidade) e camadas de grama/terra
- `data/strangelands/worldgen/biome/strange_fields.json` – cores do céu/grama, criaturas e vegetação
- `entity/SporelingEntity` e `client/Sporeling*` – a criatura nova
