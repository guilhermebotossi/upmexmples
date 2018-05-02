# SpaceWeatherSWD

## Maven Build Process

	mvn clean package install

clean -> clean the target folder from the parent project and child projects<br/>
package -> take the compiled code and package it in its distributable format, such as a JAR.<br/>
install ->  install the package into the local repository, for use as a dependency in other projects locally <br/>

## Maven Release Process

### Clean
	mvn release:clean

release:clean -> run the cleanning process of release plugin, it deletes the files created  by the release plugin

### Prepare
	mvn release:prepare
To execute this plugin your local git repository must be clean, with nothing to push for remote<br/>

release:prepare -> creates tag with current version of the project and defines the next development version, also commit and push the changes to remote(user interaction needed)

### Rollback
	mvn release:rollback

release:rollback -> if any errors  occur duriatng the execution of the "release:prepare" step, execute this goal to rollback all the changes, including the tag creation at remote

### Perform
	mvn release:perform

release:perform -> it performs the build and deploy on nexus of the recently created tag


## SonarQube execution

	mvn clean verify sonar:sonar

verify -> run any checks on results of integration tests to ensure quality criteria are met<br/>
sonar:sonar -> executes the sonar plugin with sonar goal<br/>

In order the properly function, SonarQube must be declared in the maven settings.xml <br/>
<pre><code class="xml">
	&lt;settings&gt;
	    &lt;pluginGroups&gt;
	        &lt;pluginGroup&gt;org.sonarsource.scanner.maven&lt;/pluginGroup&gt;
	    &lt;/pluginGroups&gt;
	    &lt;profiles&gt;
	        &lt;profile&gt;
	            &lt;id&gt;sonar&lt;/id&gt;
	            &lt;activation&gt;
	                &lt;activeByDefault&gt;true&lt;/activeByDefault&gt;
	            &lt;/activation&gt;
	            &lt;properties&gt;
	                &lt;!-- Optional URL to server. Default value is http://localhost:9000 --&gt;
	                &lt;sonar.host.url&gt;http://172.16.70.200:9000&lt;/sonar.host.url&gt;
	            &lt;/properties&gt;
	        &lt;/profile&gt;
	     &lt;/profiles&gt;
	&lt;/settings&gt;
</code></pre>


## PIT Mutation Testing

	mvn org.pitest:pitest-maven:mutationCoverage

org.pitest:pitest-maven:mutationCoverage -> executes the plugin org.pitest:pitest-maven with the goal mutationCoverage