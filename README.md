# ML-in-Intrusion-Detection
Master's Thesis report - Naive Bayes classification using Genetic Algorithm based Feature Selection

A Network Intrusion Detection System (NIDS) is a mechanism that detects illegal and malicious activity inside a network. Network anomaly detection is an important and dynamic topic of research. It involves comparing the suspicious malicious activity with the normal behaviour of the system. Anomaly based Intrusion detection systems use machine learning techniques to detect novel attacks. Classifiers are predictors that estimate the class label of an attack based on prior training and learning models. While it can be seen that DoS (Denial of Service) and probe attacks are filtered with reasonable accuracy, the detection rate fails miserably for R2L (Remote-to-Local) and U2R (User-to-Root) attacks. This dissertation aims to improve the accuracy of the above mentioned attacks by proposing a two-stage classifier with feature selection used in the second stage of classification. The first stage uses a simple NB classifier with all the trained features. Feature selection is the process of filtering out useful and relevant features that contribute to an attack class. The proposed feature selection technique is based on Genetic Algorithm with entropy based weights used for giving importance to each feature in the fitness function. Experiments were conducted on the NSL-KDD dataset using the WEKA machine learning tool. It can be seen that the detection rate of R2L and U2R improves significantly with 86.2% and 95% enhancement in the second stage of classification. This dissertation also compares the proposed feature selection technique with the existing filter methods and also inspects the accuracies of other classifiers.

In this dissertation, a novel two-stage Naïve Bayes classifier was proposed to enhance the detection rates of R2L and U2R attacks. As discussed in Chapter 7, it is seen that the proposed method is better than most other existing techniques, improving the detection rate significantly. The below report summarizes my work, my research findings and discusses the future scope of this undertaking.


Summary


1. Feature selection is the proposed solution to improve detection rates of R2L and U2R attacks. Meta-heuristic based evolutionary computing algorithm is used as the feature selection technique.
2. Fitness function in GA is based on entropy based weight calculation.
3. Feature selection is run over 300 generations to find 21 relevant features for R2L attacks and 22 relevant features for U2R attacks.
4. Two-stage Naïve Bayes classifier is tested on the NSL-KDD dataset. NSL-KDD dataset was used since it removes the redundant records in the KDD99 dataset so that classifiers do not produce biased results towards the more frequent records.
5. The second stage classifier is run in parallel to detect both attacks, thereby increasing speed of execution.
6. The proposed method was compared with existing techniques, feature selection techniques were compared and even Gain Ratio feature selection technique is discussed in detail.

Summary of the results
1. Overall detection rate of the proposed algorithm is 86.2% for R2L attacks and 97% for U2R attacks. This is compared with the performance of backpropagation based neural networks in [27] and it is seen that the proposed method significantly improves results.
2. Snmpguess R2L attack was the most difficult to detect with 28% detection rate. This is because the classifier was not trained properly to detect UDP protocol packets since the training set consisted of all TCP based attacks.
3. Simple NB classifier has lower detection rates than C4.5 decision tree, SOM and NB tree, in a single detection stage. However, it is faster in achieving the results than the rest.
4. However, when two-stage NB classifier is used, its performance gets upgraded drastically along with achieving those output faster than the others.
5. NB Tree has a better performance than simple NB but lower speed.
6. GA based feature selection takes a comparable time with respect to CFSSubsetEval + GeneticSearch, however it is slower than Information Gain, Gain Ratio and CFSSubsetEval + bestfirst or greedy.
7. Using GA based feature selection gives the best accuracy with GainRatioAttributeEval + Ranker algorithm coming second best with an accuracy of 56%.
8. The performance of a classifier depends upon the optimal feature subset. ‘Optimal’ does not mean having the least number of features. As seen in Figure 16 and Figure 17, Gain Ratio attribute evaluator achieves an optimal result when 8 features are selected for R2L attacks and when 15 features are selected for U2R attacks.
9. The optimal feature subset varies for each feature selection algorithm. As a result, it is difficult to determine whether a particular algorithm is better than the other. Using a feature selection technique will depend upon cost complexity, usage and the environmental settings.
10. It was found that feature no. 23 i.e count (number of records to the server) is the most important for R2L attacks.
11. Since Naïve Bayes is faster than most other machine learners, its usage in the proposed system can serve as a prototype for organizations to enhance security.

Future Scope

This algorithm was tested with the NSL-KDD dataset which is not a real life representation of a network since it consists of spurious background traffic. Analysing this algorithm with a more realistic attack traffic like the ISCX 12’ dataset can help in determining the proposed approach’s worth. Real life deployment of anomaly detectors is still a big problem. Deploying a two stage classifier will really help in analysing real time detection parameters thereby help organizations to find the most reliant cost effective anomaly detector. The proposed method can be used to improve detection rates of DoS attacks as per the need.
