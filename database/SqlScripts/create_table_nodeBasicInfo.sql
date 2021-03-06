CREATE TABLE [nodeBasicInfo] (
  [nodeID] INTEGER NOT NULL PRIMARY KEY, 
  [parentID] INT NOT NULL, 
  [name] TEXT(50) NOT NULL, 
  [description] TEXT(1000), 
  [businessArea] TEXT(50), 
  [SLOC] INT, 
  [functionPoints] INT, 
  [developmentType] TEXT(50), 
  [language] TEXT(50), 
  [languageType] TEXT(50), 
  [developmentPlatform] TEXT(50), 
  [developmentTechniques] TEXT(200), 
  [teamSize] DOUBLE NOT NULL DEFAULT 5, 
  [duration] int NOT NULL DEFAULT 180, 
  [isRoot] BOOLEAN NOT NULL, 
  [estType] TEXT(50) NOT NULL DEFAULT none);
