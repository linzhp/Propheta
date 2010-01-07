CREATE TABLE [quickEstimation] (
  [estimationID] INTEGER NOT NULL PRIMARY KEY, 
  [nodeID] INT NOT NULL, 
  [dataType] TEXT(10),  
  [formulaEffort] DOUBLE,  
  [historyEffort] DOUBLE,  
  [meanProductivity] DOUBLE,  
  [stanDevProdutivity] DOUBLE  
  );
