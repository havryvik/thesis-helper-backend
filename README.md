# Thesis helper

Backend aplikace je věřejně dostupný na adrese: https://gitlab.fel.cvut.cz/havryvik/thesis-helper

1. Příprava prostředí

   Pro instalaci musíte mít nainstalovany Git, IDE (používala jsem IntelliJ).

3. Naklonujte Git repositář a otevřete projekt ve svém IDE:

   $git clone https://gitlab.fel.cvut.cz/havryvik/thesis-helper.git

4. PostgreSQL databáze

   4.1 Pro vývoj jsem používala lokální databází, proto je potřeba provést následující kroky pro instalaci Posrtge Databáze Serveru a pro správnou konfiguraci PostrgreSQL databází.

   4.2 Nainstalujte si PostreSQL Server verze 13.2 na https://www.postgresql.org/download/. Při instalaci vyberte, že chcete instalovat i pgAdmin 4, což je gui pro Postgre. Port, kterém bude poslouchat server, můžete nechat defaultní (5432).

   4.3 Přejděte do pgAdmin, otevřete localhost server v Browseru a vytvořte si novou databázi se jménem "thesis_helper".

   4.4 V application.properties souboru projektu (složka resources) nastavte následující data:

   spring.datasource.url = jdbc:postgresql://localhost:5432/thesis_helper 	spring.datasource.username=/vami nastaveny username/ 	spring.datasource.password=/vami nastavene heslo/
   
   4.5 Po spuštění serveru (viz krok č. 6) zkontrolujte, že tabulka "role" obsahuje dva řádky s rolí "SUPERVISOR" a "ŠTUDENT". Pokud ne, ručně spustě data.sql soubor (path src/main/resources) pro naplnění této tabulky.

5. Zkontrolujte Run/Debug Spring Boot Configuration: použití Java SDK 13 a main class - cvut.fel.cz.thesis_helper.ThesisHelperApplication

6. Tlačítkem "Run" spusťte aplikaci. Aplikace běží na adrese http://localhost:8080.

**-----Použité knihovny:-----**

	spring-boot-starter-parent: 2.6.5 
	spring-boot-starter-data-jpa: (pokud není uvedená verze, je stejná jako u startového balíčku - 2.6.5)
	spring-boot-starter-data-rest
	spring-boot-starter-security
	spring-boot-starter-web
	spring-session-core
	spring-boot-devtools
	postgresql: 42.3.3
	spring-boot-configuration-processor
	lombok: 1.18.22
	jjwt: 0.9.1
