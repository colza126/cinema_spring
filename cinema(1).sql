-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 25, 2024 alle 12:26
-- Versione del server: 10.4.28-MariaDB
-- Versione PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cinema`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `elenco_generi`
--

CREATE TABLE `elenco_generi` (
  `genere` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `elenco_generi`
--

INSERT INTO `elenco_generi` (`genere`) VALUES
('COMMEDIA'),
('DRAMMATICO'),
('FANTASCIENZA'),
('HORROR'),
('SENTIMENTALE'),
('THRILLER'),
('WESTERN METROPOLITANO');

-- --------------------------------------------------------

--
-- Struttura della tabella `film`
--

CREATE TABLE `film` (
  `nome` varchar(255) NOT NULL,
  `anno_produzione` year(4) NOT NULL,
  `genere` varchar(255) NOT NULL,
  `bio` varchar(1023) NOT NULL,
  `ID` int(11) NOT NULL,
  `percorso_locandina` varchar(1024) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `film`
--

INSERT INTO `film` (`nome`, `anno_produzione`, `genere`, `bio`, `ID`, `percorso_locandina`) VALUES
('I soliti idioti 3', '2024', 'COMMEDIA', 'I soliti idioti che fanno i soliti idioti', 18, 'C:\\fakepath\\i_soliti_idioti_locandina.jpg'),
('il silenzio degli innocenti', '1980', 'HORROR', 'Persone che non hanno commesso un crimine non parlano', 20, 'C:\\fakepath\\il_silenzio_degli_innocenti_locandina.jpg'),
('star wars', '1978', 'COMMEDIA', 'Zio pera', 21, 'C:\\fakepath\\star_wars_locandina.jpg');

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `ID` int(11) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `permessi_admin` tinyint(1) NOT NULL,
  `account_confermato` tinyint(1) NOT NULL,
  `token_conferma` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`ID`, `mail`, `password`, `permessi_admin`, `account_confermato`, `token_conferma`) VALUES
(4, 'admin', '5f4dcc3b5aa765d61d8327deb882cf99', 1, 1, ''),
(46, 'colzanilorenzo@proton.me', '5f4dcc3b5aa765d61d8327deb882cf99', 0, 1, '24211ff456ddc6e3e4119256df67bc9b'),
(50, 'mailerConferma@proton.me', 'd95679752134a2d9eb61dbd7b91c4bcc', 0, 0, '3768a99c969ddbfa83c21fe2c5dd93d1');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `elenco_generi`
--
ALTER TABLE `elenco_generi`
  ADD PRIMARY KEY (`genere`);

--
-- Indici per le tabelle `film`
--
ALTER TABLE `film`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `genere` (`genere`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `mail` (`mail`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `film`
--
ALTER TABLE `film`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `film`
--
ALTER TABLE `film`
  ADD CONSTRAINT `film_ibfk_1` FOREIGN KEY (`genere`) REFERENCES `elenco_generi` (`genere`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
