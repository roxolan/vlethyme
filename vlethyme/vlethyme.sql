/*
SQLyog Ultimate v9.50 
MySQL - 5.5.15 : Database - vlethyme
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vlethyme` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `vlethyme`;

/*Table structure for table `assignment` */

DROP TABLE IF EXISTS `assignment`;

CREATE TABLE `assignment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL COMMENT '1 - Essay writing\n2 - File upload\n3 - Quiz',
  `subject` text,
  `text` text,
  `moduleId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_assignment_module1` (`moduleId`),
  CONSTRAINT `fk_assignment_module1` FOREIGN KEY (`moduleId`) REFERENCES `module` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `assignment` */

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `course` */

/*Table structure for table `course_resource` */

DROP TABLE IF EXISTS `course_resource`;

CREATE TABLE `course_resource` (
  `id` int(11) NOT NULL,
  `resourceId` int(11) NOT NULL,
  `courseId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_course_resource_resource1` (`resourceId`),
  KEY `fk_course_resource_course1` (`courseId`),
  CONSTRAINT `fk_course_resource_course1` FOREIGN KEY (`courseId`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_course_resource_resource1` FOREIGN KEY (`resourceId`) REFERENCES `resource` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `course_resource` */

/*Table structure for table `event_user` */

DROP TABLE IF EXISTS `event_user`;

CREATE TABLE `event_user` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `eventId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_event_user_user1` (`userId`),
  KEY `fk_event_user_event1` (`eventId`),
  KEY `fk_event_user_role1` (`roleId`),
  KEY `fk_event_user_group1` (`groupId`),
  CONSTRAINT `fk_event_user_event1` FOREIGN KEY (`eventId`) REFERENCES `event` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_user_group1` FOREIGN KEY (`groupId`) REFERENCES `group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_user_role1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_user_user1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `event_user` */

/*Table structure for table `forum` */

DROP TABLE IF EXISTS `forum`;

CREATE TABLE `forum` (
  `id` int(11) NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `content` text,
  `create_date` datetime DEFAULT NULL,
  `modify_date` varchar(45) DEFAULT NULL,
  `courseId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_forum_course1` (`courseId`),
  KEY `fk_forum_user1` (`userId`),
  CONSTRAINT `fk_forum_course1` FOREIGN KEY (`courseId`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_forum_user1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `forum` */

/*Table structure for table `grade` */

DROP TABLE IF EXISTS `grade`;

CREATE TABLE `grade` (
  `id` int(11) NOT NULL,
  `grade` char(1) NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `grade` */

/*Table structure for table `grade_assignment` */

DROP TABLE IF EXISTS `grade_assignment`;

CREATE TABLE `grade_assignment` (
  `id` int(11) NOT NULL,
  `userCourseId` int(11) NOT NULL,
  `assignmentId` int(11) NOT NULL,
  `gradeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usergrade_assignment` (`assignmentId`),
  KEY `fk_usergrade_usercourse` (`userCourseId`),
  KEY `fk_grade_assignment_grade1` (`gradeId`),
  CONSTRAINT `fk_grade_assignment_grade1` FOREIGN KEY (`gradeId`) REFERENCES `grade` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usergrade_assignment` FOREIGN KEY (`assignmentId`) REFERENCES `assignment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usergrade_usercourse` FOREIGN KEY (`userCourseId`) REFERENCES `user_course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `grade_assignment` */

/*Table structure for table `group` */

DROP TABLE IF EXISTS `group`;

CREATE TABLE `group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_group_user1` (`userId`),
  CONSTRAINT `fk_group_user1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `group` */

insert  into `group`(`id`,`name`,`userId`,`created_date`) values (1,'Java Group',1,'2014-03-18 14:53:50'),(2,'asdasd',1,NULL),(4,'test',1,NULL),(5,'for me',1,NULL);

/*Table structure for table `group_user` */

DROP TABLE IF EXISTS `group_user`;

CREATE TABLE `group_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  `roleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usergroup_user` (`userId`),
  KEY `fk_usergroup_group` (`groupId`),
  KEY `fk_group_user_role1` (`roleId`),
  CONSTRAINT `fk_group_user_role1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usergroup_group` FOREIGN KEY (`groupId`) REFERENCES `group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usergroup_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `group_user` */

insert  into `group_user`(`id`,`userId`,`groupId`,`roleId`) values (1,1,1,4),(2,1,2,4),(5,1,4,4),(6,1,5,4);

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from` varchar(45) DEFAULT NULL,
  `subject` varchar(45) DEFAULT NULL,
  `body` text,
  `create_date` datetime DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `parentMessageId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_message_parent` (`parentMessageId`),
  CONSTRAINT `fk_message_parent` FOREIGN KEY (`parentMessageId`) REFERENCES `message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `message` */

/*Table structure for table `messge_recepient` */

DROP TABLE IF EXISTS `messge_recepient`;

CREATE TABLE `messge_recepient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `messageId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_messge_recepient_message1` (`messageId`),
  KEY `fk_messge_recepient_user1` (`userId`),
  CONSTRAINT `fk_messge_recepient_message1` FOREIGN KEY (`messageId`) REFERENCES `message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_messge_recepient_user1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `messge_recepient` */

/*Table structure for table `module` */

DROP TABLE IF EXISTS `module`;

CREATE TABLE `module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `courseId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_resource_course` (`courseId`),
  CONSTRAINT `fk_resource_course` FOREIGN KEY (`courseId`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `module` */

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `id` int(11) NOT NULL,
  `quizId` int(11) NOT NULL,
  `text` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ques_quiz` (`quizId`),
  CONSTRAINT `fk_ques_quiz` FOREIGN KEY (`quizId`) REFERENCES `quiz` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `question` */

/*Table structure for table `quiz` */

DROP TABLE IF EXISTS `quiz`;

CREATE TABLE `quiz` (
  `id` int(11) NOT NULL,
  `assignmentId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_quiz_assign` (`assignmentId`),
  CONSTRAINT `fk_quiz_assign` FOREIGN KEY (`assignmentId`) REFERENCES `assignment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `quiz` */

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL COMMENT '1 - Room',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `resource` */

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `type` tinyint(2) DEFAULT NULL COMMENT '1 - User, 2 - Group Member',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`type`) values (1,'USER_STUDENT',1),(2,'USER_TEACHER',1),(3,'USER_MANAGER',1),(4,'GROUP_MEMBER_MANAGER',2),(5,'GROUP_MEMBER_MEMBER',2);

/*Table structure for table `room` */

DROP TABLE IF EXISTS `room`;

CREATE TABLE `room` (
  `id` int(11) NOT NULL,
  `resourceId` int(11) NOT NULL,
  `capacity` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`,`resourceId`),
  KEY `fk_room_resource1` (`resourceId`),
  CONSTRAINT `fk_room_resource1` FOREIGN KEY (`resourceId`) REFERENCES `resource` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `room` */

/*Table structure for table `scorm` */

DROP TABLE IF EXISTS `scorm`;

CREATE TABLE `scorm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `scorm` */

/*Table structure for table `survey` */

DROP TABLE IF EXISTS `survey`;

CREATE TABLE `survey` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `create_date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `survey` */

/*Table structure for table `survey_answer` */

DROP TABLE IF EXISTS `survey_answer`;

CREATE TABLE `survey_answer` (
  `id` int(11) NOT NULL,
  `questionId` int(11) NOT NULL,
  `optionId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_survey_answers_survey_question1` (`questionId`),
  KEY `fk_survey_answers_survey_options1` (`optionId`),
  KEY `fk_survey_answers_user1` (`userId`),
  CONSTRAINT `fk_survey_answers_survey_options1` FOREIGN KEY (`optionId`) REFERENCES `survey_option` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_survey_answers_survey_question1` FOREIGN KEY (`questionId`) REFERENCES `survey_question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_survey_answers_user1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `survey_answer` */

/*Table structure for table `survey_option` */

DROP TABLE IF EXISTS `survey_option`;

CREATE TABLE `survey_option` (
  `id` int(11) NOT NULL,
  `survey_question_id` int(11) NOT NULL,
  `survey_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_survey_options_survey_question1` (`survey_question_id`),
  KEY `fk_survey_options_survey1` (`survey_id`),
  CONSTRAINT `fk_survey_options_survey1` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_survey_options_survey_question1` FOREIGN KEY (`survey_question_id`) REFERENCES `survey_question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `survey_option` */

/*Table structure for table `survey_question` */

DROP TABLE IF EXISTS `survey_question`;

CREATE TABLE `survey_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` text,
  `type` tinyint(1) DEFAULT NULL COMMENT '1 - Single choice\n2 - Multiple choice',
  `surveyId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_survey_question_survey1` (`surveyId`),
  CONSTRAINT `fk_survey_question_survey1` FOREIGN KEY (`surveyId`) REFERENCES `survey` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `survey_question` */

/*Table structure for table `survey_user` */

DROP TABLE IF EXISTS `survey_user`;

CREATE TABLE `survey_user` (
  `id` int(11) NOT NULL,
  `surveyId` int(11) DEFAULT NULL,
  `groupId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `survey_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `group_user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_survey_users_survey1` (`survey_id`),
  KEY `fk_survey_users_user1` (`user_id`),
  KEY `fk_survey_users_group_user1` (`group_user_id`),
  CONSTRAINT `fk_survey_users_group_user1` FOREIGN KEY (`group_user_id`) REFERENCES `group_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_survey_users_survey1` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_survey_users_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `survey_user` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) NOT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`id`,`roleId`,`firstName`,`lastName`,`email`,`password`,`username`) values (1,1,'pmba5user','pmba5user','agabhi@gmail.com','pmba5user','pmba5user'),(2,1,'emba17user','emba17user','emba17user@gmail.com','emba17user','emba17user'),(3,1,'emba18user','emba18user','emba18user@gmail.com','emba18user','emba18user');

/*Table structure for table `user_course` */

DROP TABLE IF EXISTS `user_course`;

CREATE TABLE `user_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `courseId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `allModules` tinyint(1) NOT NULL COMMENT '0 - false\n1 - true\n',
  PRIMARY KEY (`id`),
  KEY `fk_usercourse_user` (`userId`),
  KEY `fk_usercourse_course` (`courseId`),
  KEY `fk_usercourse_role` (`roleId`),
  CONSTRAINT `fk_usercourse_course` FOREIGN KEY (`courseId`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usercourse_role` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usercourse_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user_course` */

/*Table structure for table `user_module` */

DROP TABLE IF EXISTS `user_module`;

CREATE TABLE `user_module` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `moduleId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_resource_user` (`userId`),
  KEY `fk_resource_role` (`moduleId`),
  CONSTRAINT `fk_resource_` FOREIGN KEY (`moduleId`) REFERENCES `module` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_resource_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_module_module1` FOREIGN KEY (`moduleId`) REFERENCES `module` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user_module` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
