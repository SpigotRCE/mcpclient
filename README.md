# MCPClient 1.0.0 | Minecraft Pentesting Client

![MCPClient](https://imgur.com/5F9IAtQ.jpg)


## What is MCPClient?
#### MCPClient is a Minecraft Pentesting client that allows you to connect to a Minecraft server and perform various tasks, such as impersonating another player using uuid spoofing, using custom client commands, and more. This tool is useful for pentesters who want to test the security of a Minecraft server.

## Features
- Connect to a Minecraft server
- Impersonate another player using uuid spoofing
- Custom client commands

## Installation
1. Install Java 21 or higher
2. Install Fabric loader 0.15.11 (1.21) or higher
3. Download the latest release from the [releases page](https://github.com/pedroagustinvega/mcpclient/releases)
4. Extract the zip file
5. Copy the .jar file to your Minecraft mods folder
6. Copy "mcpfiles" folder to your .minecraft folder
7. Run Minecraft with the Fabric loader profile

## Commands
- `/mcphelp` - Displays a list of commands
- `/playerl` - Displays a list of players on the actual server. Includes their UUID, game mode and ping.
- `/fakegm <mode>` - Changes your gamemode to the specified mode.

## Tips
- You can download [ViaFabric+](https://modrinth.com/mod/viafabricplus) to connect to a server with a different version than the client.