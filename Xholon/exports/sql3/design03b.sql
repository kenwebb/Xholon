/* Notes
- type = node; only one table encompassing both
- maybe type has negative id? BUT 0 = -0; maybe only XholonClass has id 0
- node has an optional "type" link to node
- type and node both have a "name", a human- and machine-readable string
 - instead of "role_name" and "type_name", call them "label"
- use single additional tables that can be used by both nodes and types: prop, link, bhvr
- what should the combined table be called? node, objk
- views
 - I could do two views of objk: node, type
- interesting mathematical fact
 - I can start at any objk, and follow zero or more links to arrive at 'XholonClass'
 - as a first step, from any objk node, I can first go to the parent or to the type
  - it's important that specific types of pathways exist, including ininite loops around identity(), eventually getting to 'XholonClass'
  - paths must commute, and otherwise obey math concepts
- in Xholon, xh.root().id() is always 0, or maybe sometimes 1 ???
- the database does not explicitly represent identity(), except somehow thru the id ???
- be sure I can apply ideas from Set Theory, Logic, CT, etc. to the database structure
- maybe start with the Application node, work down thru the whole tree, and capture everything
*/

/*
- there are multiple independent numberings, each of which may reuse the same set of non-negative integers
- nmbrng, sett, namespace, numberspace, enumeration, enum, idspace, collection
- sometimes each module might use its own app-specific overlapping numbering
- possibly these id and label values should be part of Xholon, in an XML (.xml) or Java (.java) or JavaScript (.js) file
 - see my various Mechanisms XML files
 - ex: id ranges and abbreviations for each Mechanism in Xholon
*/
DROP TABLE IF EXISTS nmbrng;
CREATE TABLE IF NOT EXISTS nmbrng (
 id integer NOT NULL,
 label varchar(80) NOT NULL,
 PRIMARY KEY (id));
INSERT INTO nmbrng (id, label) VALUES
 (0, 'node'),
 (1, 'type'),
 (2, 'cntrl'),
 (3, 'view'),
 (4, 'mech'),
 (5, 'srvc'),
 (6, 'app'),
 (7, 'etc???'),
 (1001, 'appspecific01')
 ;
/* OR n t c v m s a 1 2 3 4 5 6 7 8 9  default: n
DROP TABLE IF EXISTS nmbrng;
CREATE TABLE IF NOT EXISTS nmbrng (
 id varchar(1) NOT NULL,
 label varchar(80) NOT NULL,
 PRIMARY KEY (id));
INSERT INTO nmbrng (id, label) VALUES
 ('n', 'node'),
 ('t', 'type'),
 ('c', 'cntrl'),
 ('v', 'view'),
 ('m', 'mech'),
 ('s', 'srvc'),
 ('a', 'app'),
 ('?', 'etc???'),
 ('1', 'appspecific01')
 ;
*/

/*
- all objects live within the single Xholon tree
*/
DROP TABLE IF EXISTS objk;
CREATE TABLE IF NOT EXISTS objk (
 id integer NOT NULL,
 nmbrng_id integer NOT NULL DEFAULT 0,
 type_id integer NOT NULL DEFAULT -1, /* ??? always -1 if it's a "type"; if not specified in the INSERT statement, use default of -1 */
 parent_id integer NOT NULL,
 label varchar(80) NOT NULL,
 PRIMARY KEY (id, type_id),
 FOREIGN KEY (type_id) REFERENCES objk(id),
 FOREIGN KEY (parent_id) REFERENCES objk(id));
/* insert types, all of these should also have nmbrng 0 */
INSERT INTO objk (id, label, parent_id) VALUES
 (0, 'XholonClass', 0),
 (2000000, 'XholonMechanism', 0),
 (2000001, 'XhMechanisms', 2000000),
 (1, 'Chameleon', 0),
 (2, 'Quantity', 0),
 (5, 'PhysicalSystem', 0),
 (6, 'Universe', 0),
 (7, 'World', 0),
 (8, 'Ocean', 0),
 (9, 'Island', 0),
 (10, 'Fish', 0),
 (11, 'Fisherman', 0);
/* insert nodes, all of these should also have nmbrng 1 */
INSERT INTO objk (id, label, type_id, parent_id) VALUES
 (1, 'dummy', 6, 0),
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

DROP TABLE IF EXISTS prop;

DROP TABLE IF EXISTS link;

DROP TABLE IF EXISTS bhvr;

select * from objk;

/* show types */
select id, label, parent_id from objk
where type_id == -1;

/* show nodes */
select id, label, type_id, parent_id from objk
where type_id != -1;

