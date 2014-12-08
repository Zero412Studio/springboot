CREATE TABLE settings
(
  propertyId int not null,
  propertyName varchar(100),
  propertyValue varchar(100)
);

INSERT INTO settings (propertyId, propertyName, propertyValue)
VALUES (1, 'property.name.foo', 'foo');

INSERT INTO settings (propertyId, propertyName, propertyValue)
VALUES (2, 'property.name.bar', 'bar');
