BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "category" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "customer_order" (
	"id"	INTEGER NOT NULL,
	"amount"	REAL NOT NULL,
	"date_created"	TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
	"confirmation_number"	INTEGER NOT NULL,
	"customer_id"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("customer_id") REFERENCES "customer"("id")
);
CREATE TABLE IF NOT EXISTS "customer" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"email"	TEXT NOT NULL,
	"phone"	TEXT NOT NULL,
	"address"	TEXT NOT NULL,
	"cc_number"	TEXT NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "ordered_product" (
	"customer_order_id"	INTEGER NOT NULL,
	"product_id"	INTEGER NOT NULL,
	"quantity"	INTEGER NOT NULL DEFAULT 1,
	PRIMARY KEY("customer_order_id","product_id"),
	FOREIGN KEY("customer_order_id") REFERENCES "customer"("id"),
	FOREIGN KEY("product_id") REFERENCES "product"("id")
);
CREATE TABLE IF NOT EXISTS "product" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"price"	NUMERIC NOT NULL,
	"description"	TEXT NOT NULL,
	"category_id"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("category_id") REFERENCES "category"("id")
);
INSERT INTO "category" VALUES (1,'dairy');
INSERT INTO "category" VALUES (2,'meats');
INSERT INTO "category" VALUES (3,'bakery');
INSERT INTO "category" VALUES (4,'fruit & veg');
INSERT INTO "category" VALUES (5,'cereals');
INSERT INTO "category" VALUES (6,'drinks');
INSERT INTO "product" VALUES (1,'milk',1.7,'semi skimmed (1L)',1);
INSERT INTO "product" VALUES (2,'cheese',2.39,'mild cheddar (330g)',1);
INSERT INTO "product" VALUES (3,'butter',1.09,'unsalted (250g)',1);
INSERT INTO "product" VALUES (4,'free range eggs',1.76,'medium-sized (6 eggs)',1);
INSERT INTO "product" VALUES (5,'organic meat patties',2.29,'rolled in fresh herbs<br>2 patties (250g)',2);
INSERT INTO "product" VALUES (6,'parma ham',3.49,'matured, organic (70g)',2);
INSERT INTO "product" VALUES (7,'chicken leg',2.59,'free range (250g)',2);
INSERT INTO "product" VALUES (8,'sausages',3.55,'reduced fat, pork<br>3 sausages (350g)',2);
INSERT INTO "product" VALUES (9,'sunflower seed loaf',1.89,'600g',3);
INSERT INTO "product" VALUES (10,'sesame seed bagel',1.19,'4 bagels',3);
INSERT INTO "product" VALUES (11,'pumpkin seed bun',1.15,'4 buns',3);
INSERT INTO "product" VALUES (12,'chocolate cookies',2.39,'contain peanuts<br>(3 cookies)',3);
INSERT INTO "product" VALUES (13,'corn on the cob',1.59,'2 pieces',4);
INSERT INTO "product" VALUES (14,'red currants',2.49,'150g',4);
INSERT INTO "product" VALUES (15,'broccoli',1.29,'500g',4);
INSERT INTO "product" VALUES (16,'seedless watermelon',1.49,'250g',4);
INSERT INTO "product" VALUES (17,'jumbo oats',1.99,'Jumbo Oats (500g)',5);
INSERT INTO "product" VALUES (18,'porridge oats',2.75,'Organic Porridge Oats (1kg)',5);
INSERT INTO "product" VALUES (19,'rice flakes',2.99,'Organic Rice Flakes (500g)',5);
INSERT INTO "product" VALUES (20,'granola',3.99,'Apple & Cinnamon Granola (400g)',5);
INSERT INTO "product" VALUES (21,'herbal tea',2.5,'Herbal Tea (20 bags)',6);
INSERT INTO "product" VALUES (22,'wholebean coffee',10.75,'Organic Fairtrade Wholebean Coffee (500g)',6);
INSERT INTO "product" VALUES (23,'green tea',1.99,'Organic Green Tea (15 bags)',6);
INSERT INTO "product" VALUES (24,'organic coffee',4.75,'Organic Fairtrade Italian Roast Ground Coffee (227g)',6);
COMMIT;
