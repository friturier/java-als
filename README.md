# java-als
A Java implementation of Alternating Least Squares (ALS).
Supports:

- [x] Batch ALS
- [x] Stochastic Gradient Descent (SGD) ALS 

## installation

You can use this library as a Maven dependency by adding

```xml
<dependency>
    <groupId>org.ruivieira.ml</groupId>
    <artifactId>als</artifactId>
    <version>0.0.2</version>
</dependency>
```

to your Maven `dependencies`.
You should then specify the dependency location by using one of the following methods:

### bintray

Add the repository information to your `pom.xml`:

```xml
    <profiles>
        <profile>
            <repositories>
                <repository>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                    <id>bintray-ruivieira-maven</id>
                    <name>bintray</name>
                    <url>https://dl.bintray.com/ruivieira/maven</url>
                </repository>
                <repository>
                    <id>jcenter</id>
                    <url>https://jcenter.bintray.com/</url>
                </repository>
            </repositories>
            <pluginRepositories>
                <pluginRepository>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                    <id>bintray-ruivieira-maven</id>
                    <name>bintray-plugins</name>
                    <url>https://dl.bintray.com/ruivieira/maven</url>
                </pluginRepository>
            </pluginRepositories>
            <id>bintray</id>
        </profile>
    </profiles>
```

### locally

Or by downloading this reposity and installing the library locally by running

```text
$ mvn clean install
```

## usage

### batch ALS

A minimal example consists on parsing a ratings dataset into a collection such as `List<Rating> ratings`. Given this collection, a sparse ratings matrix can be created using

```java
SparseRealMatrix R = ALSUtils.toMatrix(ratings);
```

The initial latent factors can then be created with

```java
LatentFactors factors = LatentFactors.create(nUsers, nItems, rank);
```
We create a `BatchALS` instance

```java
BatchALS als = new BatchALS(R, rank, alpha, beta);
```
and the ALS process can then be repeated for `n` iterations:

```java
for (int iter = 0 ; iter < n ; iter++) {
	factors = als.run(factors);
}
```

After which, we can use the factors to approximate the ratings matrix, which will provide the rating _predictions_.

```java
RealMatrix nR = factors.getUsers().dot(factors.getItems());
```
