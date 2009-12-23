create table EstimationNodes (
NodeID int not null,
Name Text(50),
ParentNodeID int not null,
IsRoot boolean not null,
NodeType int not null
)