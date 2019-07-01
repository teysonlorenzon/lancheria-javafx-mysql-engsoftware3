CREATE DATABASE  IF NOT EXISTS `lancheria` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `lancheria`;
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: lancheria
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `categorias` (
  `IdCategorias` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`IdCategorias`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (4,'Bebidas'),(5,'Ingredientes'),(6,'Sorvetes');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `clientes` (
  `IdClientes` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(60) DEFAULT NULL,
  `Cidade` varchar(60) DEFAULT NULL,
  `Cep` varchar(10) DEFAULT NULL,
  `Uf` varchar(2) DEFAULT NULL,
  `Bairro` varchar(70) DEFAULT NULL,
  `Endereco` varchar(85) DEFAULT NULL,
  `Numero` int(11) DEFAULT NULL,
  `TelefoneFixo` varchar(13) DEFAULT NULL,
  `TelefoneCelular` varchar(14) DEFAULT NULL,
  `Complemento` varchar(50) DEFAULT NULL,
  `Email` varchar(70) DEFAULT NULL,
  `Cpf` varchar(18) DEFAULT NULL,
  `Rg` varchar(20) DEFAULT NULL,
  `DataNascimento` varchar(45) DEFAULT NULL,
  `Cnpj` varchar(18) DEFAULT NULL,
  `NomeFantasia` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`IdClientes`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'Sem Cadastro',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,'Testes','Campinas do Sul','99660-000','RS','Centro','Marques do Herval',345,'','(54)99999-9999','','',NULL,NULL,NULL,'12.199.743/0001-11','TestandoIMP'),(29,'Teyson Lorenzon','Campinas do Sul','99660-000','RS','Centro','Marques do Herval',345,'','(54)99183-4839','','teyson_lorenzon@hotmail.com','028.018.970-27','51165562','17/09/1997',NULL,NULL);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estoque`
--

DROP TABLE IF EXISTS `estoque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `estoque` (
  `IdEstoque` int(11) NOT NULL AUTO_INCREMENT,
  `IdProduto` int(11) DEFAULT NULL,
  `Quantidade` int(11) DEFAULT NULL,
  `IdEntrada` int(11) DEFAULT NULL,
  `IdSaida` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdEstoque`),
  KEY `refaProd_idx` (`IdProduto`),
  KEY `refEnt_idx` (`IdEntrada`),
  KEY `refSai_idx` (`IdSaida`),
  CONSTRAINT `refEnt` FOREIGN KEY (`IdEntrada`) REFERENCES `estoqueentrada` (`IdEntrada`),
  CONSTRAINT `refSai` FOREIGN KEY (`IdSaida`) REFERENCES `estoquesaida` (`IdSaida`),
  CONSTRAINT `refaProd` FOREIGN KEY (`IdProduto`) REFERENCES `produtos` (`IdProdutos`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estoque`
--

LOCK TABLES `estoque` WRITE;
/*!40000 ALTER TABLE `estoque` DISABLE KEYS */;
INSERT INTO `estoque` VALUES (57,14,20,NULL,NULL),(58,1,19,NULL,NULL),(59,15,20,NULL,NULL),(60,3,17,NULL,NULL),(61,4,17,NULL,NULL),(62,5,16,NULL,NULL),(63,6,18,NULL,NULL),(64,7,20,NULL,NULL),(65,8,18,NULL,NULL),(66,13,10,NULL,NULL);
/*!40000 ALTER TABLE `estoque` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estoqueentrada`
--

DROP TABLE IF EXISTS `estoqueentrada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `estoqueentrada` (
  `IdEntrada` int(11) NOT NULL AUTO_INCREMENT,
  `DataEntrada` varchar(45) DEFAULT NULL,
  `ValorUnitario` decimal(9,4) DEFAULT NULL,
  `Quantidade` int(11) DEFAULT NULL,
  `IdProdutos` int(11) DEFAULT NULL,
  `IdFornecedores` int(11) DEFAULT NULL,
  `IdFuncionarios` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdEntrada`),
  KEY `IdProdutos_idx` (`IdProdutos`),
  KEY `IdFornecedores_idx` (`IdFornecedores`),
  KEY `refFunc_idx` (`IdFuncionarios`),
  CONSTRAINT `IdFornecedores` FOREIGN KEY (`IdFornecedores`) REFERENCES `fornecedores` (`IdFornecedores`),
  CONSTRAINT `IdProdutos` FOREIGN KEY (`IdProdutos`) REFERENCES `produtos` (`IdProdutos`),
  CONSTRAINT `refFunc` FOREIGN KEY (`IdFuncionarios`) REFERENCES `funcionarios` (`IdFuncionarios`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estoqueentrada`
--

LOCK TABLES `estoqueentrada` WRITE;
/*!40000 ALTER TABLE `estoqueentrada` DISABLE KEYS */;
INSERT INTO `estoqueentrada` VALUES (35,'11/11/1111',10.0000,20,14,1,1),(37,'25/06/2019',20.0000,20,1,1,1),(38,'25/06/2019',10.0000,20,15,1,1),(39,'25/06/2019',10.0000,20,3,1,1),(40,'25/06/2019',20.0000,20,4,2,1),(41,'25/06/2019',20.0000,20,5,2,1),(42,'25/06/2019',20.0000,20,6,2,1),(43,'25/06/2019',20.0000,20,7,1,1),(44,'25/06/2019',20.0000,20,8,2,1),(45,'30/06/2019',20.0000,10,13,2,1);
/*!40000 ALTER TABLE `estoqueentrada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estoquesaida`
--

DROP TABLE IF EXISTS `estoquesaida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `estoquesaida` (
  `IdSaida` int(11) NOT NULL AUTO_INCREMENT,
  `DataSaida` varchar(45) DEFAULT NULL,
  `ValorUnitario` decimal(9,4) DEFAULT NULL,
  `IdLanches` int(11) DEFAULT NULL,
  `IdClientes` int(11) DEFAULT NULL,
  `IdFuncionario` int(11) DEFAULT NULL,
  `Descri` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IdSaida`),
  KEY `IdLanches_idx` (`IdLanches`),
  KEY `IdClientes_idx` (`IdClientes`),
  KEY `IdFuncionarios_idx` (`IdFuncionario`),
  CONSTRAINT `IdClientes` FOREIGN KEY (`IdClientes`) REFERENCES `clientes` (`IdClientes`),
  CONSTRAINT `IdFuncionarios` FOREIGN KEY (`IdFuncionario`) REFERENCES `funcionarios` (`IdFuncionarios`),
  CONSTRAINT `IdLanches` FOREIGN KEY (`IdLanches`) REFERENCES `lanches` (`IdLanches`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estoquesaida`
--

LOCK TABLES `estoquesaida` WRITE;
/*!40000 ALTER TABLE `estoquesaida` DISABLE KEYS */;
INSERT INTO `estoquesaida` VALUES (16,'25/06/2019',15.0000,28,1,1,'1 Hamburguer,1 Alface,1 Queijo,2 Frango,1 Pão,2 Ovos'),(18,'25/06/2019',24.0000,28,29,1,'2 Hamburguer,1 CocaCola,1 Alface,2 Queijo,2 Frango,1 Pão,2 Ovos');
/*!40000 ALTER TABLE `estoquesaida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedores`
--

DROP TABLE IF EXISTS `fornecedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fornecedores` (
  `IdFornecedores` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(60) DEFAULT NULL,
  `Cidade` varchar(60) DEFAULT NULL,
  `Cep` varchar(10) DEFAULT NULL,
  `Uf` varchar(2) DEFAULT NULL,
  `Bairro` varchar(70) DEFAULT NULL,
  `Endereco` varchar(85) DEFAULT NULL,
  `Numero` int(11) DEFAULT NULL,
  `TelefoneFixo` varchar(13) DEFAULT NULL,
  `TelefoneCelular` varchar(14) DEFAULT NULL,
  `Complemento` varchar(50) DEFAULT NULL,
  `Email` varchar(70) DEFAULT NULL,
  `Cnpj` varchar(18) DEFAULT NULL,
  `NomeFantasia` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`IdFornecedores`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedores`
--

LOCK TABLES `fornecedores` WRITE;
/*!40000 ALTER TABLE `fornecedores` DISABLE KEYS */;
INSERT INTO `fornecedores` VALUES (1,'teste','Campinas do Sul','99660-000','RS','Centro','Marques do Herval',345,'','(54)99999-9998','','ajsdggajsg@sadjah.com','23.533.128/0001-16','testeIMP'),(2,'fornec2','Campinas do Sul','99660-000','RS','Centro','Marques do Herval',345,'','(54)16546-5465','','','61.991.250/0001-25','fornecIMP');
/*!40000 ALTER TABLE `fornecedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionarios`
--

DROP TABLE IF EXISTS `funcionarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `funcionarios` (
  `IdFuncionarios` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(60) DEFAULT NULL,
  `Cidade` varchar(60) DEFAULT NULL,
  `Cep` varchar(10) DEFAULT NULL,
  `Uf` varchar(2) DEFAULT NULL,
  `Bairro` varchar(70) DEFAULT NULL,
  `Endereco` varchar(85) DEFAULT NULL,
  `Numero` int(11) DEFAULT NULL,
  `TelefoneFixo` varchar(13) DEFAULT NULL,
  `TelefoneCelular` varchar(14) DEFAULT NULL,
  `Complemento` varchar(50) DEFAULT NULL,
  `Email` varchar(70) DEFAULT NULL,
  `Cpf` varchar(18) DEFAULT NULL,
  `Rg` varchar(20) DEFAULT NULL,
  `DataNascimento` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`IdFuncionarios`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionarios`
--

LOCK TABLES `funcionarios` WRITE;
/*!40000 ALTER TABLE `funcionarios` DISABLE KEYS */;
INSERT INTO `funcionarios` VALUES (1,'Teste','Campinas do Sul','99660-000','RS','Centro','Marques do Herval',345,NULL,'(54)99183-4839',NULL,NULL,'028.018.970-27','556151515','17/09/1997'),(4,'Ze Olokinho','Campinas do Sul','99660-000','RS','Centro','Marques do Herval',345,'','(54)99654-6546','','','028.018.970-27','12312312','17/09/1997');
/*!40000 ALTER TABLE `funcionarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itenslanche`
--

DROP TABLE IF EXISTS `itenslanche`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `itenslanche` (
  `IdItensLanche` int(11) NOT NULL AUTO_INCREMENT,
  `Quantidade` int(11) DEFAULT NULL,
  `IdLanche` int(11) DEFAULT NULL,
  `IdProduto` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdItensLanche`),
  KEY `refProd_idx` (`IdProduto`),
  KEY `refLanche_idx` (`IdLanche`),
  CONSTRAINT `refLanche` FOREIGN KEY (`IdLanche`) REFERENCES `lanches` (`IdLanches`),
  CONSTRAINT `refProd` FOREIGN KEY (`IdProduto`) REFERENCES `produtos` (`IdProdutos`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itenslanche`
--

LOCK TABLES `itenslanche` WRITE;
/*!40000 ALTER TABLE `itenslanche` DISABLE KEYS */;
INSERT INTO `itenslanche` VALUES (115,1,23,6),(116,1,23,3),(117,1,23,7),(118,3,23,5),(119,1,28,4),(120,1,28,6),(121,1,28,3),(122,2,28,12),(123,1,28,8),(124,2,28,5);
/*!40000 ALTER TABLE `itenslanche` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lanches`
--

DROP TABLE IF EXISTS `lanches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `lanches` (
  `IdLanches` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) DEFAULT NULL,
  `LinkImgLanche` longtext,
  `ValorLanche` decimal(9,2) DEFAULT NULL,
  PRIMARY KEY (`IdLanches`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lanches`
--

LOCK TABLES `lanches` WRITE;
/*!40000 ALTER TABLE `lanches` DISABLE KEYS */;
INSERT INTO `lanches` VALUES (23,'x-qualquer','file:/C:/Users/Teyson/Documents/project-eclipse/lancheria-java-mysql-engenharia-software/img/imgLanches/download.png',10.00),(28,'x-teste','file:/C:/Users/Teyson/Downloads/v%20(1).png',15.00);
/*!40000 ALTER TABLE `lanches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `login` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Usuario` varchar(60) DEFAULT NULL,
  `Senha` varchar(60) DEFAULT NULL,
  `Nivel` varchar(30) DEFAULT NULL,
  `IdFuncionario` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `refereFunc_idx` (`IdFuncionario`),
  CONSTRAINT `refereFunc` FOREIGN KEY (`IdFuncionario`) REFERENCES `funcionarios` (`IdFuncionarios`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (1,'admin','21232f297a57a5a743894a0e4a801fc3','Admin',1),(15,'funcionario','cc7a84634199040d54376793842fe035','Usuario',4);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produtos`
--

DROP TABLE IF EXISTS `produtos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `produtos` (
  `IdProdutos` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(50) DEFAULT NULL,
  `Preco` decimal(8,2) DEFAULT NULL,
  `IdCategorias` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdProdutos`),
  KEY `IdCategorias_idx` (`IdCategorias`),
  CONSTRAINT `IdCategorias` FOREIGN KEY (`IdCategorias`) REFERENCES `categorias` (`IdCategorias`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produtos`
--

LOCK TABLES `produtos` WRITE;
/*!40000 ALTER TABLE `produtos` DISABLE KEYS */;
INSERT INTO `produtos` VALUES (1,'CocaCola',6.00,4),(3,'Queijo',1.00,5),(4,'Hamburguer',2.00,5),(5,'Ovos',1.00,5),(6,'Alface',0.50,5),(7,'Baccon',1.50,5),(8,'Pão',1.00,5),(12,'Frango',2.00,5),(13,'Pepsi',5.00,4),(14,'Sukita',5.00,4),(15,'Fanta Lanranja',5.00,4);
/*!40000 ALTER TABLE `produtos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saidaprod`
--

DROP TABLE IF EXISTS `saidaprod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `saidaprod` (
  `IdSaidaProd` int(11) NOT NULL AUTO_INCREMENT,
  `Quantidade` int(11) DEFAULT NULL,
  `IdLanche` int(11) DEFAULT NULL,
  `IdProduto` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdSaidaProd`),
  KEY `reLanche_idx` (`IdLanche`),
  KEY `reProd_idx` (`IdProduto`),
  CONSTRAINT `reLanche` FOREIGN KEY (`IdLanche`) REFERENCES `lanches` (`IdLanches`),
  CONSTRAINT `reProd` FOREIGN KEY (`IdProduto`) REFERENCES `produtos` (`IdProdutos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saidaprod`
--

LOCK TABLES `saidaprod` WRITE;
/*!40000 ALTER TABLE `saidaprod` DISABLE KEYS */;
/*!40000 ALTER TABLE `saidaprod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'lancheria'
--

--
-- Dumping routines for database 'lancheria'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-30  3:45:05
