java -Dconfig.file=./application.conf ^
     -Dlogback.configurationFile=./logback.xml ^
     -jar ./load_generator-assembly-1.0.jar ^
     -s com.scenarios.ClosedModelScenario