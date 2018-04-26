# Git-Cloner
With Git-Cloner, you can clone all repositories from an Github user or an Github 
organisation.
## Usage
Read USAGE.MD
## Use GitCloner as a Library
From GitCloner v1.1.0 you can use GitCloner as a library!
Example for an user:
```
GitCloner cloner = new GitCloner("GitPotTime", AccountType.USER);
// Start the cloner
cloner.start();
```
And for an organisation:
```
GitCloner cloner = new GitCloner("Prylox", AccountType.ORG);
// Start the cloner
cloner.start();
```
## Dependencies
Git-Cloner have some dependencies like projectlombok v1.16.20 and gson v2.8.2

## Java version
GitCloner need Java 7 or newer to work

## What do I need to do
Read TODO.MD