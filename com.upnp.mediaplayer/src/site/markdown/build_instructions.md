MediaPlayer uses [Maven](http://maven.apache.org) to build the project.

On this page, we are trying to show some use-cases and howto use Maven to solve those. Most of the stuff is pretty standard
and should be known, if you know Maven already.

# Project-Structure

MediaPlayer uses a common multi-module approach. This means, that the master pom is contained in the root directory of
the project, and all other modules are in separate folders beneath. The root directory does contain a src-folder where
the site and its pages (e.g. the one you are looking at currently) are stored in.

We use Markdown for these pages (see [Github Markdown Basics](https://help.github.com/articles/markdown-basics) for some
hints, on how to use this.

The [core-module](/MediaPlayer/mediaplayer/index.html) does provide the basic framework and the application for our project.
All other modules are plugins, which do provide some interesting functionality, but do not have to be used. You can
decide yourself, if you would like to download and install those.

In the root-folder you will also find a repo sub-folder which contains some dependencies, which are not stored in a
central Maven Repository in the Internet. These will hopefully get removed and will get stored in our own repository.

# Maven settings

To be able to deploy the site and/or releases to our own repositories, you will need to provide user credentials. Since
those are specific for you, they are not provided inside the project pom, but in your local settings.xml, which you can
find in ~/.m2 usually.

The settings.xml should look something like the following:

```
<settings>
  <servers>
    <server>
      <id>bintray</id>
      <username>BINTRAY-USERNAME</username>
      <password>BINTRAY-API-KEY</password>
    </server>

    <server>
        <id>libs-releases</id>
        <username>BINTRAY-USERNAME</username>
        <password>BINTRAY-API-KEY</password>
    </server>
    <server>
        <id>libs-snapshots</id>
        <username>BINTRAY-USERNAME</username>
        <password>BINTRAY-API-KEY</password>
    </server>
      <server>
          <id>oss-jfrog-artifactory</id>
          <username>BINTRAY-USERNAME</username>
          <password>BINTRAY-API-KEY</password>
      </server>

      <server>
          <id>github</id>
          <username>GITHUB-USERNAME</username>
          <password>GITHUB-PASSWORD</password>
      </server>
  </servers>
</settings>

```

# Build the whole project

To build the whole project, it is recommended to call the following command inside the root-folder of the project:

```
mvn clean install
```

This will generate all necessary jar-files for the project. All dependencies are downloaded from the Maven Repository or
are fetched from our local repository (the already mentioned repo sub-folder).

If you would like to do some debugging with specific debugging settings (eg. loglevel debug), then you can call the above
command with the ```debug``` profile:

```
mvn clean install -Pdebug
```

The provided properties can be easily adopted inside of the master pom.xml in the root directory of the project.

# Publish the project site

Maven allows us to provide a project site. This page is based on several reports, which Maven provides from itself.
Furthermore, we added some pages (like this one) in the Markdown-Format. These will get transferred to HTML and can be
viewed then via any browser.

The page can get generated and published with the following command:

```
mvn site:site site:attach-descriptor site:stage scm-publish:publish-scm
```

Please note, that you do have to provide some user credentials to publish the site. These credentials should be provided
in your local settings.xml file, like explained above. You can test the whole site-generation, by just providing the
command

```
mvn site:site site:attach-descriptor site:stage
```

You will then find the generated site in the target/staging-folder of the root-project folder.

# Create a release

When creating a release, a new version of the project is created. While during the usual development the project version
ends on -SNAPSHOT, a release version does not contain this addendum.

A release can be created with the following command:

```
mvn release:prepare
```

You need to provide the new release version as well as the version after the release. This is very easy, in that you only
need to press enter to use the default values :-).

Next you need to perform the real release:

```
mvn release:perform
```

Note, that this step can take some time, since the whole repository is cloned.

After this, the site should be published for the created release, this can be done with the following commands:



