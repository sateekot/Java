Issue:
Type: IDE mvn build
Cannot analyze dependencies: zip file is empty maven

Solution:

1. Go to your local repository (.m2/repository)
2. Run command: 
	find . -iname "*.jar" -type f -empty | xargs rm
