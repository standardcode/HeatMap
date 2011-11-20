SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `heatmap`
--

-- --------------------------------------------------------

--
-- Table structure for table `domain`
--

CREATE TABLE `domain` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `domain` varchar(255) NOT NULL,
  `appear` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `mousedown`
--

CREATE TABLE `mousedown` (
  `url_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `x` smallint(6) NOT NULL,
  `y` smallint(6) NOT NULL,
  `appear` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mousemove_url` (`url_id`),
  KEY `fk_mousedown_user1` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `mousemove`
--

CREATE TABLE `mousemove` (
  `url_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `x` smallint(6) NOT NULL,
  `y` smallint(6) NOT NULL,
  `appear` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mousemove_url` (`url_id`),
  KEY `fk_mousemove_user1` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `mouseup`
--

CREATE TABLE `mouseup` (
  `url_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `x` smallint(6) NOT NULL,
  `y` smallint(6) NOT NULL,
  `appear` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mousemove_url` (`url_id`),
  KEY `fk_mouseup_user1` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `url`
--

CREATE TABLE `url` (
  `domain_id` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` tinytext NOT NULL,
  `appear` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_url_domain1` (`domain_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `session` varchar(45) NOT NULL,
  `appear` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `session_UNIQUE` (`session`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `mousedown`
--
ALTER TABLE `mousedown`
  ADD CONSTRAINT `fk_mousedown_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_mousemove_url0` FOREIGN KEY (`url_id`) REFERENCES `url` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `mousemove`
--
ALTER TABLE `mousemove`
  ADD CONSTRAINT `fk_mousemove_url` FOREIGN KEY (`url_id`) REFERENCES `url` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_mousemove_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `mouseup`
--
ALTER TABLE `mouseup`
  ADD CONSTRAINT `fk_mousemove_url00` FOREIGN KEY (`url_id`) REFERENCES `url` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_mouseup_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `url`
--
ALTER TABLE `url`
  ADD CONSTRAINT `fk_url_domain1` FOREIGN KEY (`domain_id`) REFERENCES `domain` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
