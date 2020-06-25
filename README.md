Head Pet Plugin #Spigot 1.8
---

Easy player head utility item

### Configuration

This plugin can be configured in `dev.yml` in the plugin folder.
To find this file, load the plugin at least once in your server.

Here are the attributes and description in the configuration file.

| Attributes | Type | Description |
| --- | --- | --- |
| devMode | Boolean | Set `true` to enable dev command like `/pet`. |
| logDebug | Boolean | Set `true` to make log become more verbose. |


### Head Configuration
We can configure each head (aka a utility head) to have a specific function based on their configuration file.
To make an utility head, create `<utility name>.yml` file in the plugin folder.
The file name is case-sensitive, and the head can be obtained using `/pet <utility head name>`.
For example, `Fortune Diamond.yml` is the configuration description for `Fortune Diamond` head and can be
obtained from `/pet Fortune Diamond`.

In the configuration file, there are three compulsory fields and two other optional fields.
Here is an example of a complete configuration:

```yaml
texture: ewogICJ0aW1lc3RhbXAiIDogMTU5MzA3NTM2MDA2MCwKICAicHJvZmlsZUlkIiA6ICIyYzEwNjRmY2Q5MTc0MjgyODRlM2JmN2ZhYTdlM2UxYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJOYWVtZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83YTFiNDkwM2NlZmI2MTk1NjE0OTdmZGNiODllZWMzNDgwYTIzYmVmZTkzZGY0MzE3MWQ0OTEwYTVlMjZkZDYwIgogICAgfQogIH0KfQ
command: kill @s
devMode: true
description: Instantly send you to heaven.
color: b
```

The first field is `texture`. There is the <u>texture data</u> for the head encoded in Base64.
This encoded text can be obtained from [Mineskin.org][1].

The second field is `command`. This is for setting the command to run when a player has interacted with the head.
Identifier variables of `@a` (all online player), `@s` (executing player), and `@r` (random player) are supported.

Finally, the last compulsory field is `devMode`. This configures the head to be obtainable in a `devMode==true`
environment only.

The `description` and `color` fields are compulsory fields. They are for setting head description and display name color
respectively. The color code is a one-letter HEX value of native minecraft [formatting codes][2].

All these fields must occupy only one line in the configuration file.


[1]: https://mineskin.org
[2]: https://minecraft.gamepedia.com/Formatting_codes#Color_codes
