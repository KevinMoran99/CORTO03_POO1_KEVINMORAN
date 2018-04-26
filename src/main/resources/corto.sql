
-- --------------------------------------------------------
-- Host:                         localhost
-- Versión del servidor:         5.6.24-log - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.4.0.5125
-- --------------------------------------------------------

-- Volcando estructura para tabla corto.libros
CREATE TABLE IF NOT EXISTS `libros` (
  `codi_libr` int(11) NOT NULL AUTO_INCREMENT,
  `nomb_libr` varchar(250) NOT NULL,
  `auto_libr` varchar(250) DEFAULT NULL,
  `gene_libr` varchar(100) DEFAULT NULL,
  `anio_libr` int(4) DEFAULT NULL,
  `esta_libr` int(1) DEFAULT NULL,
  PRIMARY KEY (`codi_libr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando estructura para tabla corto.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `codi_usua` int(11) NOT NULL AUTO_INCREMENT,
  `nomb_usua` varchar(250) NOT NULL,
  `acce_usua` varchar(250) NOT NULL,
  `cont_usua` varchar(250) NOT NULL,
  PRIMARY KEY (`codi_usua`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Volcando estructura para tabla corto.prestamos
CREATE TABLE IF NOT EXISTS `prestamos` (
  `codi_pres` int(11) NOT NULL AUTO_INCREMENT,
  `codi_libr` int(11) DEFAULT NULL,
  `codi_usua` int(11) DEFAULT NULL,
  `fech_pres` datetime DEFAULT NULL,
  `fech_devo` datetime DEFAULT NULL,
  PRIMARY KEY (`codi_pres`),
  KEY `fk_pres_libr` (`codi_libr`),
  KEY `fk_pres_usua` (`codi_usua`),
  CONSTRAINT `fk_pres_libr` FOREIGN KEY (`codi_libr`) REFERENCES `libros` (`codi_libr`),
  CONSTRAINT `fk_pres_usua` FOREIGN KEY (`codi_usua`) REFERENCES `usuarios` (`codi_usua`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Volcando datos para la tabla dropbox.usuarios: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`codi_usua`, `nomb_usua`, `acce_usua`, `cont_usua`) VALUES
	(1, 'Juan', 'juanito', '123');
	
INSERT INTO `usuarios` (`codi_usua`, `nomb_usua`, `acce_usua`, `cont_usua`) VALUES
	(null, 'Lucas', 'Luqin', '456');
	
INSERT INTO `usuarios` (`codi_usua`, `nomb_usua`, `acce_usua`, `cont_usua`) VALUES
	(null, 'Mario', 'Mariano', '789');
	
INSERT INTO libros VALUES (null, 'Harry Potter', 'JK Rowling', 'Fantasía', 1999, 1);
INSERT INTO libros VALUES (null, 'El Señor de los Anillos', 'Tolkien', 'Fantasía', 1998, 1);
INSERT INTO libros VALUES (null, 'La Biblia', 'Varios sujetos', 'Religioso', 100, 1);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
