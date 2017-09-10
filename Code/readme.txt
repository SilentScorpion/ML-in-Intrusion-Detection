/* Two-Stage Naive Bayes Classifier with feature selection 
#Two Stage classifier is used to improve detection rates of R2L and U2R attacks.
#The first stage is a simple NB classifier implemented in Testing.java
#WEKA classes are imported in the file 
#weka.core.instances;
#weka.classifier.bayes.NaiveBayes
#weka.core.Attribute;
#The NaiveBayes class in the classifier package implements the Naive Bayes for binomial classes with parameters set to default
#The kernel estimator function is set to false and it uses uniform Gaussian distribution after calculating the mean and the 
standard deviation for each attribute. 
#The classindex is set to the last attribute i.e class (normal,DoS,U2R,R2L,probe).
#Output is obtained in the form of a confusion matrix in Testing.java
/*

/*
#Stage 2 is implemented in BayesClassifiertwo.java
#It uses a simple NB classifier with feature selection.
#Feature selection is implemented in FeatureSelection.java
#It is implemented using Genetic Algorithm
#Weights for fitness function are calculated in WeightsOfFeatures.java
#Entropy based weight calculation for fitness of a chromosome.


