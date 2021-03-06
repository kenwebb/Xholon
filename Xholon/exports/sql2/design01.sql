/* 
To view this file, download an open-source relational database product such as mysql or postgresql or sqlite.

Automatically generated by Xholon version 0.9.1, using Xholon2Sql2.java
Thu Dec 19 11:02:47 GMT-500 2019 1576771367116
model: based on Universe of Fish
www.primordion.com/Xholon
In MySQL, you will need to temporarily disable foreign keys while loading in this file:
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS=0;
source thenameofthisfile.sql;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
 */

/* Notes
- I could combine types and nodes into a single table
 - xhcName and RoleName would just be "name"
 - typeID would be the node type, or null for types
 - BUT types and nodes both use the same set of IDs
- deal with fact that prop values have a type  ex: double precision
- Nodes Node nodes node
- roleName vs rolename vs role_name
- ID vs id vs _id
*/

/* Naming Conventions
https://github.com/RootSoft/Database-Naming-Convention
- singular table names
 - ex: fisherman
- lowercase snakecase
 - ex: middle_name
- 
*/

/* nodes Xholon: CSH IXholon node  */
DROP TABLE IF EXISTS nodes;
CREATE TABLE IF NOT EXISTS nodes (
 ID integer NOT NULL,
 roleName varchar(80) NOT NULL,
 typeID integer NOT NULL,
 parentID integer,
 PRIMARY KEY (ID),
 FOREIGN KEY (typeID) REFERENCES types(ID),
 FOREIGN KEY (parentID) REFERENCES nodes(ID));
INSERT INTO nodes (ID, roleName, typeID, parentID) VALUES
 (46, 'uni', 6, 0),
 (49, 'wrld', 7, 46),
 (50, 'ocn', 8, 49),
 (151, 'isle1', 9, 50),
 (157, 'isle2', 9, 50),
 (152, 'f1', 11, 151),
 (153, 'f2', 11, 151),
 (154, 'f3', 11, 151),
 (155, 'f4', 11, 151),
 (156, 'f5', 11, 151),
 (158, 'f6', 11, 157),
 (159, 'f7', 11, 157),
 (160, 'f8', 11, 157),
 (161, 'f9', 11, 157),
 (162, 'f10', 11, 157);
select * from nodes;

/* node properties; what about type properties? */
DROP TABLE IF EXISTS props;
CREATE TABLE IF NOT EXISTS props (
 nodeID integer NOT NULL,
 propName varchar(80) NOT NULL,
 propType varchar(20) NOT NULL, /* a valid database type? or a Java or JavaScript type? or a Xholon Attribute_ type? */
 propValue varchar(1000) NOT NULL, /* TODO it could be an array or JS/JSON Object */
 PRIMARY KEY (nodeID, propName),
 FOREIGN KEY (nodeID) REFERENCES nodes(ID));
INSERT INTO props (nodeID, propName, propType, propValue) VALUES
 (49, 'fprob', 'double precision OR double OR Number', '0.1'),
 (49, 'aString', 'String', 'This is some text.'),
 (49, 'anArray', 'ArrayOfString', '[one,two,three]'),
 (49, 'anObject', 'JSON', '{something: "some value"}'),
 (151, 'color', 'String', 'green'),
 (200, 'abc', 'String', 'Invalid nodeID');
select * from props;

/** node links */
DROP TABLE IF EXISTS links;

/** JavaScript or Avatar or other behaviors */
DROP TABLE IF EXISTS bhvrs;

/* types Xholon: IH IXholonClass xhc */
DROP TABLE IF EXISTS types;
CREATE TABLE IF NOT EXISTS types (
 ID integer NOT NULL,
 xhcName varchar(80) NOT NULL,
 parentID integer,
 PRIMARY KEY (ID),
 FOREIGN KEY (parentID) REFERENCES types(ID));
INSERT INTO types (ID, xhcName, parentID) VALUES
 (0, 'XholonClass', 0),
 (2000000, 'XholonMechanism', 0),
 (2000001, 'XhMechanisms', 2000000),
 (2000002, 'XholonMechanisms', 2000000),
 (2000003, 'UserMechanisms', 2000000),
 (2000004, 'UserMechanism', 2000000),
 (1010000, 'Annotation', 2000000),
 (1002200, 'Attribute', 2000000),
 (1002201, 'Attribute_attribute', 1002200),
 (1002202, 'Attribute_boolean', 1002200),
 (1002203, 'Attribute_byte', 1002200),
 (1002204, 'Attribute_char', 1002200),
 (1002205, 'Attribute_double', 1002200),
 (1002206, 'Attribute_float', 1002200),
 (1002207, 'Attribute_int', 1002200),
 (1002208, 'Attribute_long', 1002200),
 (1002209, 'Attribute_Object', 1002200),
 (1014910, 'default', 1002209),
 (1002210, 'Attribute_short', 1002200),
 (1002211, 'Attribute_String', 1002200),
 (1002212, 'Attribute_arrayboolean', 1002200),
 (1002213, 'Attribute_arraybyte', 1002200),
 (1002214, 'Attribute_arraychar', 1002200),
 (1002215, 'Attribute_arraydouble', 1002200),
 (1002216, 'Attribute_arrayfloat', 1002200),
 (1002217, 'Attribute_arrayint', 1002200),
 (1002218, 'Attribute_arraylong', 1002200),
 (1002219, 'Attribute_arrayObject', 1002200),
 (1002220, 'Attribute_arrayshort', 1002200),
 (1002221, 'Attribute_arrayString', 1002200),
 (1002222, 'Attribute_List', 1002200),
 (1002223, 'Attribute_Map', 1002200),
 (1002224, 'Attribute_Set', 1002200),
 (1002225, 'attr:Attribute_boolean', 1002200),
 (1002226, 'attr:Attribute_byte', 1002200),
 (1002227, 'attr:Attribute_char', 1002200),
 (1002228, 'attr:Attribute_double', 1002200),
 (1002229, 'attr:Attribute_float', 1002200),
 (1002230, 'attr:Attribute_int', 1002200),
 (1002231, 'attr:Attribute_long', 1002200),
 (1002232, 'attr:Attribute_Object', 1002200),
 (1002233, 'attr:Attribute_short', 1002200),
 (1002234, 'attr:Attribute_String', 1002200),
 (1014400, 'Avatar', 2000000),
 (1014401, 'Chatbot', 2000000),
 (1014402, 'Sprite', 2000000),
 (1014403, 'Stage', 2000000),
 (1001200, 'Behavior', 2000000),
 (1001201, 'OpaqueBehavior', 1001200),
 (14, 'Universebehavior', 1001200),
 (15, 'Oceanbehavior', 1001200),
 (16, 'Fishermanbehavior', 1001200),
 (1016400, 'BehaviorTreeEntity', 2000000),
 (1016401, 'RootBT', 1016400),
 (1016402, 'CompositeBT', 1016400),
 (1016403, 'SequenceBT', 1016402),
 (1016404, 'SelectorBT', 1016402),
 (1016405, 'ParallelBT', 1016402),
 (1016406, 'DecoratorBT', 1016400),
 (1016407, 'InverterBT', 1016406),
 (1016408, 'LeafBT', 1016400),
 (1016409, 'ActionBT', 1016408),
 (1016410, 'ConditionBT', 1016408),
 (1016411, 'SuccessBT', 1016408),
 (1016412, 'FailureBT', 1016408),
 (1016413, 'RunningBT', 1016408),
 (1013100, 'BigraphEntity', 2000000),
 (1013101, 'BigraphicalReactiveSystem', 1013100),
 (1013102, 'Bigraphs', 1013100),
 (1013103, 'Bigraph', 1013100),
 (1013104, 'PlaceGraph', 1013100),
 (1013105, 'LinkGraph', 1013100),
 (1013106, 'ReactionRulesBG', 1013100),
 (1013107, 'ReactionRuleBG', 1013100),
 (1013108, 'Redex', 1013100),
 (1013109, 'Reactum', 1013100),
 (1013110, 'PlaceBG', 1013100),
 (1013111, 'RootBG', 1013110),
 (1013112, 'NodeBG', 1013110),
 (1013113, 'SiteBG', 1013100),
 (1013114, 'LinkBG', 1013100),
 (1013115, 'HyperedgeBG', 1013114),
 (1013116, 'OuterNameBG', 1013114),
 (1013117, 'PointBG', 1013100),
 (1013118, 'PortBG', 1013117),
 (1013119, 'InnerNameBG', 1013117),
 (1013120, 'ControlBG', 1013100),
 (1013121, 'ComposerBG', 1013100),
 (1003200, 'BusApp', 2000000),
 (1003201, 'AppClientMVC', 1003200),
 (1003202, 'AppClientModel', 1003200),
 (1003203, 'AppClientView', 1003200),
 (1003204, 'AppClientController', 1003200),
 (1003205, 'AppClientWorker', 1003200),
 (1003206, 'AppClientBusinessDelegate', 1003200),
 (1003207, 'BusAppService', 1003200),
 (1003208, 'BusAppDao', 1003200),
 (1003209, 'BusAppEjb', 1003200),
 (1015900, 'CatTheory', 2000000),
 (1015901, 'CatAql', 1015900),
 (1015902, 'CatOpDsl', 1015900),
 (1004200, 'DfdEntity', 2000000),
 (1004201, 'DfdExternalEntity', 1004200),
 (1004202, 'DfdDataFlow', 1004200),
 (1004203, 'DfdProcess', 1004200),
 (1004204, 'DfdDataStore', 1004200),
 (1004205, 'DfdContainer', 1004200),
 (1004206, 'DfdExternalEntities', 1004205),
 (1004207, 'DfdDataFlows', 1004205),
 (1004208, 'DfdProcesses', 1004205),
 (1004209, 'DfdDataStores', 1004205),
 (1014900, 'gexf', 2000000),
 (1014901, 'attributeGEXF', 1014900),
 (1014902, 'attributes', 1014900),
 (1014903, 'attvalue', 1014900),
 (1014904, 'attvalues', 1014900),
 (1014905, 'edge', 1014900),
 (1014906, 'edges', 1014900),
 (1014907, 'graph', 1014900),
 (1014908, 'node', 1014900),
 (1014909, 'nodes', 1014900),
 (1016200, 'HypergraphEntity', 2000000),
 (1016201, 'Hyperedge', 1016200),
 (1010300, 'Listener', 2000000),
 (1010301, 'ListenerList', 1010300),
 (1014800, 'NaturalLanguage', 2000000),
 (1014801, 'GoogleNL', 1014800),
 (1014802, 'OpenCalais', 1014800),
 (1014803, 'Sentimood', 1014800),
 (1014804, 'SentiEmoLex', 1014800),
 (1014000, 'NoSql', 2000000),
 (1014001, 'NoSqlClient', 1014000),
 (1014002, 'Neo4jClient', 1014000),
 (1003000, 'OrNode', 2000000),
 (1003001, 'OrNodeNext', 1003000),
 (1003002, 'OrNodeRandom', 1003000),
 (1013000, 'PetriNetEntity', 2000000),
 (1013001, 'PetriNet', 1013000),
 (1013002, 'PlacePN', 1013000),
 (1013003, 'PlaceBoolean', 1013002),
 (1013004, 'PlaceInteger', 1013002),
 (1013005, 'PlaceStructured', 1013002),
 (1013006, 'Species', 1013002),
 (1013007, 'TransitionPN', 1013000),
 (1013008, 'Reaction', 1013007),
 (1013009, 'QueueTransitions', 1013000),
 (1013010, 'ArcPN', 1013000),
 (1013011, 'InputArc', 1013010),
 (1013012, 'OutputArc', 1013010),
 (1013013, 'Transitions', 1013000),
 (1013014, 'Reactions', 1013013),
 (1013015, 'Places', 1013000),
 (1013016, 'Speciez', 1013015),
 (1013017, 'InputArcs', 1013000),
 (1013018, 'OutputArcs', 1013000),
 (1013019, 'AnalysisPN', 1013000),
 (1013020, 'AnalysisPetriNet', 1013019),
 (1013021, 'AnalysisCRN', 1013019),
 (1013022, 'AnalysisCat', 1013019),
 (1013023, 'AnalysisMemComp', 1013019),
 (1013024, 'PneBehavior', 1013000),
 (1013025, 'PlaceBehavior', 1013024),
 (1013026, 'MovingPlaceBehavior', 1013025),
 (1013027, 'TransitionBehavior', 1013024),
 (1013028, 'MovingTransitionBehavior', 1013027),
 (1013029, 'FiringTransitionBehavior', 1013027),
 (1013030, 'QueueNodesInGrid', 1013000),
 (1013031, 'GridOwner', 1013000),
 (1013032, 'ConstantlyStirredPot', 1013031),
 (1013033, 'MembranePN', 1013000),
 (1000100, 'Port', 2000000),
 (1014500, 'RemoteNode', 2000000),
 (1014501, 'CrossApp', 1014500),
 (1014502, 'MessageChannel', 1014500),
 (1014503, 'PeerJS', 1014500),
 (1014504, 'PostMessage', 1014500),
 (1014505, 'WebRTC', 1014500),
 (1010100, 'Role', 2000000),
 (1016300, 'RecipeEntity', 2000000),
 (1016301, 'RecipeBook', 1016300),
 (1016302, 'MinecraftStyleRecipeBook', 1016301),
 (1016303, 'JsonRulesEngineRecipeBook', 1016301),
 (1016304, 'CeptreJsonRulesEngineRecipeBook', 1016301),
 (1016000, 'RoomEntity', 2000000),
 (1016001, 'RoomETrice', 1016000),
 (1014300, 'Script', 2000000),
 (1014301, 'script', 1014300),
 (1014600, 'SpreadsheetEntity', 2000000),
 (1014601, 'Workbook', 1014600),
 (1014602, 'Spreadsheet', 1014600),
 (1014603, 'SpreadsheetRow', 1014600),
 (1014604, 'SpreadsheetCell', 1014600),
 (1014605, 'SpreadsheetFormula', 1014600),
 (1014606, 'Srw', 1014600),
 (1014607, 'Scl', 1014600),
 (1014608, 'Sfr', 1014600),
 (1000000, 'StateMachineEntity', 2000000),
 (1000001, 'StateMachine', 1000000),
 (1000002, 'Region', 1000000),
 (1000003, 'Vertex', 1000000),
 (1000004, 'ConnectionPointReference', 1000003),
 (1000005, 'State', 1000003),
 (1000006, 'FinalState', 1000005),
 (1000007, 'Pseudostate', 1000003),
 (1000008, 'PseudostateTerminate', 1000007),
 (1000009, 'PseudostateShallowHistory', 1000007),
 (1000010, 'PseudostateJunction', 1000007),
 (1000011, 'PseudostateJoin', 1000007),
 (1000012, 'PseudostateInitial', 1000007),
 (1000013, 'PseudostateFork', 1000007),
 (1000014, 'PseudostateExitPoint', 1000007),
 (1000015, 'PseudostateEntryPoint', 1000007),
 (1000016, 'PseudostateDeepHistory', 1000007),
 (1000017, 'PseudostateChoice', 1000007),
 (1000018, 'Trigger', 1000000),
 (1000019, 'Transition', 1000000),
 (1000020, 'TransitionExternal', 1000019),
 (1000021, 'TransitionInternal', 1000019),
 (1000022, 'TransitionLocal', 1000019),
 (1000023, 'Activity', 1000000),
 (1000024, 'Guard', 1000000),
 (1000025, 'EntryActivity', 1000000),
 (1000026, 'ExitActivity', 1000000),
 (1000027, 'DoActivity', 1000000),
 (1000028, 'DeferrableTrigger', 1000000),
 (1000029, 'Target', 1000000),
 (1016500, 'SubtreesEntity', 2000000),
 (1016501, 'BehaviorsST', 1016500),
 (1016502, 'InventoryST', 1016500),
 (1016503, 'ToolsST', 1016500),
 (1010200, 'Uid', 2000000),
 (1016100, 'XholonBase', 2000000),
 (1016101, 'XholonNull', 1016100),
 (1016102, 'MathObject', 1016100),
 (1004100, 'XholonCollection', 2000000),
 (1004101, 'XholonList', 1004100),
 (1004102, 'XholonMap', 1004100),
 (1004103, 'XholonSet', 1004100),
 (1011200, 'XholonFramework', 2000000),
 (1011201, 'InheritanceHierarchy', 1011200),
 (1011202, 'TreeNodeFactory', 1011200),
 (1011203, 'Queue', 1011200),
 (1011204, 'SynchronizedQueue', 1011200),
 (1011205, 'XholonNode', 1011200),
 (1011206, 'Message', 1011200),
 (1010700, 'XholonService', 2000000),
 (1010701, 'XholonInternalService', 1010700),
 (1010702, 'ServiceLocatorService', 1010701),
 (1010703, 'XholonDirectoryService', 1010701),
 (1010704, 'DummyService', 1010701),
 (1010705, 'TreeProcessorService', 1010701),
 (1010706, 'AboutService', 1010701),
 (1010707, 'ChartViewerService', 1010701),
 (1010708, 'SpringFrameworkService', 1010701),
 (1010709, 'NodeSelectionService', 1010701),
 (1010710, 'XholonHelperService', 1010701),
 (1010711, 'WiringService', 1010701),
 (1010712, 'ControlService', 1010701),
 (1010713, 'ScriptService', 1010701),
 (1010714, 'CssService', 1010701),
 (1010715, 'MediaService', 1010701),
 (1010716, 'GameEngineService', 1010701),
 (1010717, 'NetLogoService', 1010701),
 (1010718, 'SvgViewService', 1010701),
 (1010719, 'GistService', 1010701),
 (1010720, 'ExternalFormatService', 1010701),
 (1010721, 'SearchEngineService', 1010701),
 (1010722, 'RecordPlaybackService', 1010701),
 (1010723, 'SemanticWebService', 1010701),
 (1010724, 'SbmlService', 1010701),
 (1010725, 'MathSciEngService', 1010701),
 (1010726, 'BlackboardService', 1010701),
 (1010727, 'VfsService', 1010701),
 (1010728, 'NoSqlService', 1010701),
 (1010729, 'RestService', 1010701),
 (1010730, 'BlueprintsService', 1010701),
 (1010731, 'XmlDatabaseService', 1010701),
 (1010732, 'JcrService', 1010701),
 (1010733, 'JavaSerializationService', 1010701),
 (1010734, 'BerkeleyDbService', 1010701),
 (1010735, 'TimelineService', 1010701),
 (1010736, 'ValidationService', 1010701),
 (1010737, 'XmlValidationService', 1010701),
 (1010738, 'DatabaseService', 1010701),
 (1010739, 'GenericViewerService', 1010701),
 (1010740, 'GridViewerService', 1010701),
 (1010741, 'VrmlService', 1010701),
 (1010742, 'GraphicalNetworkViewerService', 1010701),
 (1010743, 'LogService', 1010701),
 (1010744, 'ReflectionService', 1010701),
 (1010745, 'Xholon2XmlService', 1010701),
 (1010746, 'XholonCreationService', 1010701),
 (1010747, 'XmlReaderService', 1010701),
 (1010748, 'XmlWriterService', 1010701),
 (1010749, 'Xml2XholonService', 1010701),
 (1010750, 'XPathService', 1010701),
 (1010751, 'XQueryService', 1010701),
 (1010752, 'MeteorPlatformService', 1010701),
 (1010753, 'RemoteNodeService', 1010701),
 (1010754, 'BroadcastService', 1010701),
 (1010755, 'SpreadsheetService', 1010701),
 (1010756, 'NaturalLanguageService', 1010701),
 (1010757, 'RecipeService', 1010701),
 (1010758, 'HTML5Service', 1010701),
 (1010759, 'WebCryptographyService', 1010758),
 (1010760, 'IndexedDBService', 1010758),
 (1010761, 'Xholon2Svg', 1010701),
 (1010762, 'Xholon2Properties', 1010701),
 (1010763, 'XholonInternalServices', 1010700),
 (1010764, 'XholonUserService', 1010700),
 (1010765, 'XholonUserServices', 1010700),
 (1010766, 'XholonServiceImpl', 1010700),
 (1010800, 'XPath', 2000000),
 (1010801, 'PcsPath', 1010800),
 (2000100, 'XholonViewer', 0),
 (1000200, 'DataPlotter', 2000100),
 (1000201, 'XYChart', 1000200),
 (1000202, 'XAxisLabel', 1000200),
 (1000203, 'YAxisLabel', 1000200),
 (1000204, 'XYSeries', 1000200),
 (1000500, 'GridViewer', 2000100),
 (1002100, 'HistogramViewer', 2000100),
 (1002101, 'XAxisLabel_Hist', 1002100),
 (1002102, 'YAxisLabel_Hist', 1002100),
 (1000600, 'Interactions', 2000100),
 (1000700, 'Snapshot', 2000100),
 (1011600, 'SvgEntity', 2000100),
 (1011601, 'SvgLabel', 1011600),
 (1011602, 'SvgViewable', 1011600),
 (1011603, 'SvgAttribute', 1011600),
 (1011604, 'SvgClient', 1011600),
 (1000800, 'TextTree', 2000100),
 (1, 'Chameleon', 0),
 (2, 'Quantity', 0),
 (5, 'PhysicalSystem', 0),
 (6, 'Universe', 0),
 (7, 'World', 0),
 (8, 'Ocean', 0),
 (9, 'Island', 0),
 (10, 'Fish', 0),
 (11, 'Fisherman', 0);
select * from types;

/* create a Fisherman table by querying the other tables */
select roleName || ':' || xhcName || '_' || nodes.ID as name from types, nodes
where types.ID = nodes.typeID
and types.xhcName = 'Fisherman';

