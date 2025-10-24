-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: hotel
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `alocacao_vaga`
--

DROP TABLE IF EXISTS `alocacao_vaga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alocacao_vaga` (
  `id` int NOT NULL AUTO_INCREMENT,
  `obs` varchar(100) NOT NULL,
  `status` varchar(1) NOT NULL,
  `veiculo_id` int NOT NULL,
  `vaga_estacionamento_id` int NOT NULL,
  `check_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_alocacao_vaga_veiculo1_idx` (`veiculo_id`),
  KEY `fk_alocacao_vaga_vaga_estacionamento1_idx` (`vaga_estacionamento_id`),
  KEY `fk_alocacao_vaga_check1_idx` (`check_id`),
  CONSTRAINT `fk_alocacao_vaga_check1` FOREIGN KEY (`check_id`) REFERENCES `check` (`id`),
  CONSTRAINT `fk_alocacao_vaga_vaga_estacionamento1` FOREIGN KEY (`vaga_estacionamento_id`) REFERENCES `vaga_estacionamento` (`id`),
  CONSTRAINT `fk_alocacao_vaga_veiculo1` FOREIGN KEY (`veiculo_id`) REFERENCES `veiculo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `caixa`
--

DROP TABLE IF EXISTS `caixa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `caixa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `valor_de_abertura` float NOT NULL,
  `valor_de_fechamento` float NOT NULL,
  `data_hora_abertura` datetime NOT NULL,
  `data_hora_fechamento` datetime NOT NULL,
  `obs` varchar(100) NOT NULL,
  `status` varchar(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `check`
--

DROP TABLE IF EXISTS `check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_hora_cadastro` datetime NOT NULL,
  `data_hora_entrada` datetime NOT NULL,
  `data_hora_saida` datetime NOT NULL,
  `obs` varchar(100) NOT NULL,
  `status` varchar(1) NOT NULL,
  `check_quarto_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_check_check_quarto1_idx` (`check_quarto_id`),
  CONSTRAINT `fk_check_check_quarto1` FOREIGN KEY (`check_quarto_id`) REFERENCES `check_quarto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `check_hospede`
--

DROP TABLE IF EXISTS `check_hospede`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_hospede` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo_hospede` varchar(45) NOT NULL,
  `obs` varchar(100) NOT NULL,
  `status` varchar(1) NOT NULL,
  `check_id` int NOT NULL,
  `hospede_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_check_hospede_check1_idx` (`check_id`),
  KEY `fk_check_hospede_hospede1_idx` (`hospede_id`),
  CONSTRAINT `fk_check_hospede_check1` FOREIGN KEY (`check_id`) REFERENCES `check` (`id`),
  CONSTRAINT `fk_check_hospede_hospede1` FOREIGN KEY (`hospede_id`) REFERENCES `hospede` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `check_quarto`
--

DROP TABLE IF EXISTS `check_quarto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_quarto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_hora_inicio` datetime NOT NULL,
  `data_hora_fim` datetime NOT NULL,
  `obs` varchar(45) NOT NULL,
  `status` varchar(1) NOT NULL,
  `quarto_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_check_quarto_quarto1_idx` (`quarto_id`),
  CONSTRAINT `fk_check_quarto_quarto1` FOREIGN KEY (`quarto_id`) REFERENCES `quarto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `copa_quarto`
--

DROP TABLE IF EXISTS `copa_quarto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `copa_quarto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantidade` float NOT NULL,
  `data_hora_pedido` datetime NOT NULL,
  `obs` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `check_quarto_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_copa_quarto_check_quarto1_idx` (`check_quarto_id`),
  CONSTRAINT `fk_copa_quarto_check_quarto1` FOREIGN KEY (`check_quarto_id`) REFERENCES `check_quarto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fornecedor`
--

DROP TABLE IF EXISTS `fornecedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fornecedor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `fone` varchar(14) NOT NULL,
  `fone2` varchar(14) NOT NULL,
  `email` varchar(100) NOT NULL,
  `cep` varchar(10) NOT NULL,
  `logradouro` varchar(100) NOT NULL,
  `bairro` varchar(45) NOT NULL,
  `cidade` varchar(45) NOT NULL,
  `complemento` varchar(100) NOT NULL,
  `data_cadastro` datetime NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `rg` varchar(14) NOT NULL,
  `obs` varchar(100) NOT NULL,
  `status` varchar(1) NOT NULL,
  `usuario` varchar(45) DEFAULT NULL,
  `senha` varchar(45) DEFAULT NULL,
  `razao_social` varchar(100) NOT NULL,
  `cnpj` varchar(18) NOT NULL,
  `inscricao_estadual` varchar(15) NOT NULL,
  `contato` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcionario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `fone` varchar(14) NOT NULL,
  `fone2` varchar(14) NOT NULL,
  `email` varchar(100) NOT NULL,
  `cep` varchar(10) NOT NULL,
  `logradouro` varchar(100) NOT NULL,
  `bairro` varchar(45) NOT NULL,
  `cidade` varchar(45) NOT NULL,
  `complemento` varchar(100) NOT NULL,
  `data_cadastro` datetime NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `rg` varchar(14) NOT NULL,
  `obs` varchar(100) NOT NULL,
  `status` varchar(1) NOT NULL,
  `usuario` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hospede`
--

DROP TABLE IF EXISTS `hospede`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospede` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `fone` varchar(14) NOT NULL,
  `fone2` varchar(14) NOT NULL,
  `email` varchar(100) NOT NULL,
  `cep` varchar(10) NOT NULL,
  `logradouro` varchar(100) NOT NULL,
  `bairro` varchar(45) NOT NULL,
  `cidade` varchar(45) NOT NULL,
  `complemento` varchar(100) NOT NULL,
  `data_cadastro` datetime DEFAULT NULL,
  `cpf` varchar(14) NOT NULL,
  `rg` varchar(14) NOT NULL,
  `obs` varchar(100) NOT NULL,
  `status` varchar(1) NOT NULL,
  `usuario` varchar(45) DEFAULT NULL,
  `senha` varchar(45) DEFAULT NULL,
  `razao_social` varchar(100) NOT NULL,
  `cnpj` varchar(18) NOT NULL,
  `inscricao_estadual` varchar(15) NOT NULL,
  `contato` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `marca`
--

DROP TABLE IF EXISTS `marca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `marca` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  `status` varchar(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `modelo`
--

DROP TABLE IF EXISTS `modelo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `modelo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  `status` varchar(1) NOT NULL,
  `marca_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_modelo_marca1_idx` (`marca_id`),
  CONSTRAINT `fk_modelo_marca1` FOREIGN KEY (`marca_id`) REFERENCES `marca` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `movimento_caixa`
--

DROP TABLE IF EXISTS `movimento_caixa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimento_caixa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_hora_movimento` datetime NOT NULL,
  `valor` float NOT NULL,
  `descricao` varchar(100) NOT NULL,
  `obs` varchar(100) NOT NULL,
  `status` varchar(1) NOT NULL,
  `caixa_id` int NOT NULL,
  `receber_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_movimento_caixa_caixa_idx` (`caixa_id`),
  KEY `fk_movimento_caixa_receber1_idx` (`receber_id`),
  CONSTRAINT `fk_movimento_caixa_caixa` FOREIGN KEY (`caixa_id`) REFERENCES `caixa` (`id`),
  CONSTRAINT `fk_movimento_caixa_receber1` FOREIGN KEY (`receber_id`) REFERENCES `receber` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oderm_servico`
--

DROP TABLE IF EXISTS `oderm_servico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oderm_servico` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_hora_cadastro` datetime NOT NULL,
  `data_hora_prevista_inicio` datetime NOT NULL,
  `data_hora_prevista_termino` datetime NOT NULL,
  `obs` varchar(100) NOT NULL,
  `status` varchar(1) NOT NULL,
  `check_id` int NOT NULL,
  `servico_id` int NOT NULL,
  `quarto_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_oderm_servico_check1_idx` (`check_id`),
  KEY `fk_oderm_servico_servico1_idx` (`servico_id`),
  KEY `fk_oderm_servico_quarto1_idx` (`quarto_id`),
  CONSTRAINT `fk_oderm_servico_check1` FOREIGN KEY (`check_id`) REFERENCES `check` (`id`),
  CONSTRAINT `fk_oderm_servico_quarto1` FOREIGN KEY (`quarto_id`) REFERENCES `quarto` (`id`),
  CONSTRAINT `fk_oderm_servico_servico1` FOREIGN KEY (`servico_id`) REFERENCES `servico` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `produto_copa`
--

DROP TABLE IF EXISTS `produto_copa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produto_copa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `decricao` varchar(100) NOT NULL,
  `valor` float NOT NULL,
  `codigo_barra` varchar(50) DEFAULT NULL,
  `obs` varchar(100) DEFAULT NULL,
  `status` varchar(1) NOT NULL,
  `copa_quarto_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_produto_copa_copa_quarto1_idx` (`copa_quarto_id`),
  CONSTRAINT `fk_produto_copa_copa_quarto1` FOREIGN KEY (`copa_quarto_id`) REFERENCES `copa_quarto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quarto`
--

DROP TABLE IF EXISTS `quarto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quarto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  `capacidade_hospedes` int NOT NULL,
  `metragem` float NOT NULL,
  `identificacao` varchar(45) NOT NULL,
  `andar` int NOT NULL,
  `flag_animais` tinyint NOT NULL,
  `obs` varchar(100) NOT NULL,
  `status` varchar(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `receber`
--

DROP TABLE IF EXISTS `receber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receber` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_hora_cadastro` datetime NOT NULL,
  `valor_original` float NOT NULL,
  `desconto` float NOT NULL,
  `acrescimo` float NOT NULL,
  `valor_pago` float NOT NULL,
  `obs` varchar(100) NOT NULL,
  `status` varchar(1) NOT NULL,
  `check_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_receber_check1_idx` (`check_id`),
  CONSTRAINT `fk_receber_check1` FOREIGN KEY (`check_id`) REFERENCES `check` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reserva`
--

DROP TABLE IF EXISTS `reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserva` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_hora_reserva` datetime NOT NULL,
  `data_prevista_entrada` date NOT NULL,
  `data_prevista_saida` date NOT NULL,
  `obs` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `check_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reserva_check1_idx` (`check_id`),
  CONSTRAINT `fk_reserva_check1` FOREIGN KEY (`check_id`) REFERENCES `check` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reserva_quarto`
--

DROP TABLE IF EXISTS `reserva_quarto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserva_quarto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_hora_inicio` datetime NOT NULL,
  `data_hora_fim` datetime NOT NULL,
  `obs` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `reserva_id` int NOT NULL,
  `quarto_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reserva_quarto_reserva1_idx` (`reserva_id`),
  KEY `fk_reserva_quarto_quarto1_idx` (`quarto_id`),
  CONSTRAINT `fk_reserva_quarto_quarto1` FOREIGN KEY (`quarto_id`) REFERENCES `quarto` (`id`),
  CONSTRAINT `fk_reserva_quarto_reserva1` FOREIGN KEY (`reserva_id`) REFERENCES `reserva` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `servico`
--

DROP TABLE IF EXISTS `servico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servico` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  `valor` float NOT NULL,
  `obs` varchar(100) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vaga_estacionamento`
--

DROP TABLE IF EXISTS `vaga_estacionamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vaga_estacionamento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  `obs` varchar(100) NOT NULL,
  `metragem_vaga` float NOT NULL,
  `status` varchar(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `veiculo`
--

DROP TABLE IF EXISTS `veiculo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `veiculo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `placa` varchar(7) NOT NULL,
  `cor` varchar(45) NOT NULL,
  `modelo_id` int NOT NULL,
  `funcionario_id` int DEFAULT NULL,
  `fornecedor_id` int DEFAULT NULL,
  `hospede_id` int DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_veiculo_modelo1_idx` (`modelo_id`),
  KEY `fk_veiculo_funcionario1_idx` (`funcionario_id`),
  KEY `fk_veiculo_fornecedor1_idx` (`fornecedor_id`),
  KEY `fk_veiculo_hospede1_idx` (`hospede_id`),
  CONSTRAINT `fk_veiculo_fornecedor1` FOREIGN KEY (`fornecedor_id`) REFERENCES `fornecedor` (`id`),
  CONSTRAINT `fk_veiculo_funcionario1` FOREIGN KEY (`funcionario_id`) REFERENCES `funcionario` (`id`),
  CONSTRAINT `fk_veiculo_hospede1` FOREIGN KEY (`hospede_id`) REFERENCES `hospede` (`id`),
  CONSTRAINT `fk_veiculo_modelo1` FOREIGN KEY (`modelo_id`) REFERENCES `modelo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'hotel'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-24 20:20:07
