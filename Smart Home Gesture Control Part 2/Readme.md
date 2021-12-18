**CSE 535 – MOBILE COMPUTING**

**MIDTERM**

**SMART HOME GESTURE CONTROL APPLICATION - PART 2**

 

**Objective:** 

Develop a python application which would recognize the hand gestures recorded during the assignment 2 and store the recognized gestures in a csv file. 

**Technology Stack Used:**

·     Python 3.8

·     Tensor Flow

·     OpenCV 

·     Keras

·     Scipy 

·     Numpy

**Given:**

·     Trained CNN-model, which recognizes the alphabet gestures.

·     HandShapeFeatureExtractor Class, which uses the CNN model to extract the hand gesture by resizing the frame extracted to 200x200.

·     FrameExtractor Class, which processes the video to extract the frame at the middle of the video length. (Assumption: The gesture is visible at the middle frame).

**Approach / Solution:**

\1.    Store all the 51 videos recorded in the train data folder.

\2.    Collect and store the location of all videos in sorted manner in an array using glob.

\3.    Use the FrameExtractor class to extract the middle frames for all the videos and store it in a folder named frames.

\4.    Now to generate the feature vector, we need to first process the extracted frame.

\5.    The frame needs to be converted from RGB to Gray Scale using the OpenCV’s cvtColor.

\6.    Now the cnn_model.h5 is being loaded (using keras with tensorflow) and is being used to predict and store it in the feature matrix.

\7.    The dimensionality reduction is being done using the numpy.squeeze so as a ease to calculate the cosine similarity at a later stage.

\8.    The test videos are being stored in the test folder. 

\9.    The Steps from 2 to 7 are repeated for the generation of the test feature matrix.

\10.  Now the cosine similarity is being calculated for each of the test feature matrix element with each element of the train feature matrix. Later the minimum distance is being used. The cosine similarity is being calculated by scipy.spatial which uses 1d numpy array. Another alternative option could have been the sklearn’s cosine_distances.

\11.  Finally, the result is being stored in the Results.csv file. 