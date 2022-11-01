CREATE DATABASE  IF NOT EXISTS `dotap_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `dotap_db`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dotap_db
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `perfis`
--

DROP TABLE IF EXISTS `perfis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfis` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfis`
--

LOCK TABLES `perfis` WRITE;
/*!40000 ALTER TABLE `perfis` DISABLE KEYS */;
INSERT INTO `perfis` VALUES (1,'Colaborador'),(2,'Administrador');
/*!40000 ALTER TABLE `perfis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registros`
--

DROP TABLE IF EXISTS `registros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registros` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_hora` datetime NOT NULL,
  `status` int NOT NULL DEFAULT '1',
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_registros_2` (`id_usuario`),
  CONSTRAINT `FK_registros_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registros`
--

LOCK TABLES `registros` WRITE;
/*!40000 ALTER TABLE `registros` DISABLE KEYS */;
INSERT INTO `registros` VALUES (3,'2022-10-30 08:00:00',1,1),(5,'2022-10-30 14:30:00',1,1),(6,'2022-10-30 13:30:00',1,1),(7,'2022-10-30 17:00:00',1,1),(8,'2022-10-31 09:00:00',1,1),(9,'2022-10-31 12:00:00',1,1),(10,'2022-10-31 13:00:00',1,1),(11,'2022-10-31 18:00:00',1,1),(12,'2022-11-01 09:44:00',1,1),(13,'2022-11-01 13:44:00',1,1),(14,'2022-10-16 09:35:00',1,1),(15,'2022-10-17 11:56:00',1,1),(16,'2022-10-17 18:30:00',1,1),(17,'2022-10-17 19:30:00',1,1),(18,'2022-11-02 09:13:00',1,1),(19,'2022-11-02 12:44:00',1,1),(20,'2022-11-02 13:50:00',1,1),(21,'2022-11-02 18:21:00',1,1),(23,'2022-11-03 21:21:44',1,1),(24,'2022-10-09 18:00:00',1,1),(25,'2022-10-09 18:20:00',1,1),(26,'2022-11-03 14:00:00',1,1);
/*!40000 ALTER TABLE `registros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitacoes`
--

DROP TABLE IF EXISTS `solicitacoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitacoes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_hora` datetime NOT NULL,
  `id_usuario` int NOT NULL,
  `id_tipo` int NOT NULL,
  `id_status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_solicitacoes_2` (`id_usuario`),
  KEY `FK_solicitacoes_3` (`id_status`),
  KEY `FK_solicitacoes_4` (`id_tipo`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitacoes`
--

LOCK TABLES `solicitacoes` WRITE;
/*!40000 ALTER TABLE `solicitacoes` DISABLE KEYS */;
INSERT INTO `solicitacoes` VALUES (1,'2022-10-09 14:09:00',1,1,1),(2,'2022-10-09 09:30:00',1,1,1),(3,'2022-10-09 18:00:00',1,1,2),(20,'2022-11-01 09:44:00',1,2,1),(21,'2022-11-03 14:00:00',1,1,2),(24,'2022-11-02 18:21:00',1,2,1),(25,'2022-11-03 07:42:00',1,1,1),(26,'2022-11-08 06:43:00',1,1,1);
/*!40000 ALTER TABLE `solicitacoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitacoes_registros_altera`
--

DROP TABLE IF EXISTS `solicitacoes_registros_altera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitacoes_registros_altera` (
  `id_solicitacao` int NOT NULL,
  `id_registro` int NOT NULL,
  PRIMARY KEY (`id_solicitacao`,`id_registro`),
  KEY `FK_solicitacoes_registros_altera_3_idx` (`id_registro`),
  CONSTRAINT `FK_solicitacoes_registros_altera_2` FOREIGN KEY (`id_solicitacao`) REFERENCES `solicitacoes` (`id`),
  CONSTRAINT `FK_solicitacoes_registros_altera_3` FOREIGN KEY (`id_registro`) REFERENCES `registros` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitacoes_registros_altera`
--

LOCK TABLES `solicitacoes_registros_altera` WRITE;
/*!40000 ALTER TABLE `solicitacoes_registros_altera` DISABLE KEYS */;
INSERT INTO `solicitacoes_registros_altera` VALUES (20,12),(24,21),(3,24),(21,26);
/*!40000 ALTER TABLE `solicitacoes_registros_altera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_solicitacoes`
--

DROP TABLE IF EXISTS `status_solicitacoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status_solicitacoes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_solicitacoes`
--

LOCK TABLES `status_solicitacoes` WRITE;
/*!40000 ALTER TABLE `status_solicitacoes` DISABLE KEYS */;
INSERT INTO `status_solicitacoes` VALUES (1,'Pedente'),(2,'Aprovada'),(3,'Recusada');
/*!40000 ALTER TABLE `status_solicitacoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_solicitacoes`
--

DROP TABLE IF EXISTS `tipo_solicitacoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_solicitacoes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_solicitacoes`
--

LOCK TABLES `tipo_solicitacoes` WRITE;
/*!40000 ALTER TABLE `tipo_solicitacoes` DISABLE KEYS */;
INSERT INTO `tipo_solicitacoes` VALUES (1,'Inclusão'),(2,'Exclusão');
/*!40000 ALTER TABLE `tipo_solicitacoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `sobrenome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `status` int NOT NULL DEFAULT '1',
  `id_perfil` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_usuarios_2` (`id_perfil`),
  CONSTRAINT `FK_usuarios_2` FOREIGN KEY (`id_perfil`) REFERENCES `perfis` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Cleyson','Silva','cleyson.silva@email.com','123456',1,1);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-01 18:12:31
