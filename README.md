# Java Web Scraper

- Design a web scraper using an existing Java api
- Configure it to what we want or add more functions
- Do some data processing (i.e. which news sites contain articles about pop artists)

Using: <a href="http://jaunt-api.com/index.htm">Jaunt</a> and <a href="http://www.oracle.com/technetwork/java/javamail/index.html">JavaMail</a>

###Development:
1. Make an issue with the bug you're fixing or enchancement your doing and assign yourself or assign yourself an existing issue.
2. Go to Slack and msg bubbot with ```pr <issue #> from WebScraper```, a new branch would be created a long with a pull request based off the issue. 
3. Run ```git fetch```.
4. Run ```git checkout <branch name>``` using the branch you have just created. It'll be appended with the issue #. For example ```pr 1 from WebScraper``` would create a branch called ```iss1```.

###Execution:
1. Clone this repository and download <a href="http://jaunt-api.com/download.htm">Jaunt</a> to the root of this repository.
2. Go into the main directory: ```cd src/main```
3. To Compile: ```javac -cp jaunt0.9.9.9/jaunt0.9.9.9.jar;jaf-1.1.1/activation.jar;javaemail-1.4.5/mail.jar Email.java MangaFox.java MangaStream.java MangaHere.java Scrape.java ```
4. To Run: ```java -cp jaunt0.9.9.9/jaunt0.9.9.9.jar;.;jaf-1.1.1/activation.jar;javaemail-1.4.5/mail.jar Scrape```
