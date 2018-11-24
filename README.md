# SavageGui

A powerful menu api for bukkit

- Lightweight
- Flexible
- Support a seamless inventory open
- The ability to create an animated menu

### Integration
#### For maven:
```xml
<repositories>
  <repository>
    <id>zenegix-repo</id>
    <url>https://repo.zenegix.ru/content/groups/public/</url>
  </repository>
</repositories>

<dependencies>
    <!-- Menu api -->
    <dependency>
        <groupId>ru.zenegix.menu</groupId>
        <artifactId>core</artifactId>
        <version>1.3-SNAPSHOT</version>
    </dependency>
  
   <!-- Seamless opening. Optional -->
    <dependency>
        <groupId>ru.zenegix.menu</groupId>
        <artifactId>seamless-opening</artifactId>
        <version>1.3-SNAPSHOT</version>
    </dependency>
</dependencies>
```
#### For gradle:
```gradle
repositories {
  maven {
    url 'https://repo.zenegix.ru/content/groups/public/'
  }
}

dependencies {
    compile 'ru.zenegix.menu:core:1.3-SNAPSHOT'
    compile 'ru.zenegix.menu:seamless-opening:1.3-SNAPSHOT' // Optional
}
```

### Usage:

For first step, you need to create a MenuManager:
```java
MenuManager menuManager = new MenuManager(plugin);
```

Or, if you want to use custom open processor (for example SeamlessMenuOpenProcessor):
```java 
MenuManager menuManager = new MenuManager(plugin, SeamlessMenuOpenProcessorFactory.get(plugin))
```

On second step, you need to create a menu window:
```java
MenuWindow menuWindow = this.menuManager.createWindowBuilder()
        .setTitle(session -> "Hello, " + session.getOwner().getName())
        .setSize(18)
        .addItems(StaticMenuItem.builder()
                .setMenuIcon(new SimpleMenuIcon(0, new ItemStack(Material.STONE)))
                .build()
        ).build();
```

On threed step, create a menu template:
```java
MenuTemplate template = new SingleMenuTemplate(menuWindow);
```

Now, you can open your menu template for player:
```java
menuManager.open(player, template);
```
