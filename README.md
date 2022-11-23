## RPGアイテム作成プラグイン
RPGで使えるアイテムを作製するプラグインです。ymlでアイテムを追加・編集することができます。

注意:他のレシピ追加プラグインと競合する可能性があります。
### ymlファイルの書き方
ファイルは`RPGItems/item`の下に保存してください。
まず例を2つ掲載します。
```yaml
id: 0
name: §l§gStepping Stone
material: DIAMOND
execute:
  - setblock ~-1 ~-1 ~-1 glass
  - setblock ~-1 ~-1 ~ glass
  - setblock ~-1 ~-1 ~1 glass
  - setblock ~ ~-1 ~-1 glass
  - setblock ~ ~-1 ~ glass
  - setblock ~ ~-1 ~1 glass
  - setblock ~1 ~-1 ~-1 glass
  - setblock ~1 ~-1 ~ glass
  - setblock ~1 ~-1 ~1 glass
  - particle minecraft:bubble_pop ~ ~ ~ 1 1 1 0 100 force @a
  - playsound minecraft:item.armor.equip_chain player @p
lore:
  - §3そらもとべるはず
recipe:
    mapping:
        D: DIAMOND
        G: GOLD_INGOT
    shape:
      - " D "
      - "DGD"
      - " D "
```
```yaml
id: 1
name: 守護石
material: IRON_INGOT
execute: 
  - effect give @p minecraft:regeneration 1 10
  - effect give @p minecraft:resistance 1 10
  - delete
lore:
  - カチカチの石
  - ここぞというときにつかおう
recipe:
    mapping:
        S: STONE
        D: DIAMOND
    shape:
        - "SSS"
        - "SDS"
        - "SSS"
```
ハイフン(-)から始まるファイルは無視されます。そのほかファイル名はなんでも良いですが、英数字を使用することを推奨します。そうでない場合、環境によってはゲーム内の表示が崩れる可能性があります。
* 内部ID
* アイテム名
* Lore（説明文）
* アイテムの素材（見た目）
* 右クリック時に発動させるコマンド
  の5つの項目があります。順に説明します。
#### 内部ID
`id: <内部ID: String>`とかけます。例えば、`id: 578f5b01-27b3-7a72-867b-dac4c357988a`などです。ただしこのIDはアイテム固有のものである必要があります。すでに削除したアイテムであっても、ワールド内に残っていた場合競合するので注意してください。
(マジカル・ワンドのIDを0としていたが、これを変更して新しくマジカル・リングのIDを0とした場合、ワールド内に残っているマジカル・ワンドはマジカル・リングとして使えるようになってしまうということです。)
#### アイテム名
`name: <アイテム名: String>`とかけます。例えば、`name: ひのきのぼう`などです。セクションで装飾することで色を変更したり様々な効果を与えられます。
#### Lore
```yaml
lore:
    - ひのきで できた ぼう
    - こうげき +3
    - かいふく +20
```
#### アイテムの素材(見た目)
`material: <素材名: String>`とかけます。例えば、`material: STICK`などです。どのアイテムがどの素材名と対応しているかは、以下を参照してください。
https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html
#### 右クリック時に発動させるコマンド
```yaml
execute:
  - コマンド1
  - コマンド2
  - コマンド3
```
とかけます。実行者はアイテムを持っているプレイヤーです。op権限は無視してなんでも実行されるので気をつけてください。
プラグイン側ではコマンドが成功したかどうかは判定しないので、デバッグの際は慎重に行ってください。
また、特殊なコマンドとして`delete`を追加しています。これは自身をプレイヤーのインベントリから削除するという意味です。
消耗アイテムを制作する場合には役立ちます。
#### レシピ
クラフトレシピを設定することができます。
レシピが重複した場合、後から読み込まれたほうが優先されます。バニラのレシピについても上書きされます。
書式は少し複雑ですが、
```yaml    
recipe:
    mapping:
        S: STICK
    shape:
        top:" S "
        middle:" S "
        bottom:" S "
```
のようにかけます。レシピ配置を表す記号はアルファベットが推奨です。また、mappingに設定されていない文字は空白を表します。
### コマンド
#### アイテムを入手する
`/rpgitems give <player> <item-id> <amount?>` ※amountは指定しない場合1になります。
#### アイテムの説明を表示する
`/rpgitems descript <item-id>`
#### アイテムのコンフィグを再読込する
`/rpgitems reload`
