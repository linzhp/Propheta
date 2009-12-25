CREATE TABLE [nodeBasicInfo] (
  [nodeID] INTEGER PRIMARY KEY, 
  [parentID] INT NOT NULL, 
  [name] TEXT(50) NOT NULL, 
  [description] TEXT(1000), 
  [businessArea] TEXT(50), 
  [SLOC] INT NOT NULL, 
  [functionPoints] INT NOT NULL, 
  [developmentType] TEXT(50), 
  [language] TEXT(50), 
  [languageType] TEXT(50), 
  [developmentPlatform] TEXT(50), 
  [developmentTechniques] TEXT(200), 
  [teamSize] DOUBLE NOT NULL DEFAULT 5, 
  [duration] int NOT NULL DEFAULT 180, 
  [estEffort] DOUBLE, 
  [estPDR] DOUBLE, 
  [estProductivity] DOUBLE, 
  [estPM] DOUBLE, 
  [estPersons] INT, 
  [estOthersInfo] BLOB, 
  [cocomoEM] DOUBLE, 
  [cocomoSCED] DOUBLE, 
  [isRoot] BOOLEAN NOT NULL
);
