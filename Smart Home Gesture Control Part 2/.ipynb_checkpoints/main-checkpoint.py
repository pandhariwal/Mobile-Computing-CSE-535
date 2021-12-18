#!/usr/bin/env python
# coding: utf-8

# In[76]:


import cv2
import numpy as np
import os
import sys
import glob
import tensorflow as tf
import scipy.spatial as sp
from handshape_feature_extractor import HandShapeFeatureExtractor
from frameextractor import frameExtractor
from numpy import genfromtxt
from numpy import dot
from numpy.linalg import norm


# In[77]:


trainfiles = []

trainfiles = glob.glob(os.path.join(os.path.join('traindata'), "*.mp4"))
sorted(trainfiles)
count = 0
for each in trainfiles:
    frameExtractor(each, os.path.join(os.getcwd(), "frames"), count)
    count += 1
frames_path = os.path.join(os.getcwd(), "frames")
files = []
files = glob.glob(os.path.join(frames_path, "*.png"))
sorted(files)
train_vectors = []

for each in files:
    png = cv2.imread(each)
    png = cv2.cvtColor(png, cv2.COLOR_BGR2GRAY)
    results = HandShapeFeatureExtractor.get_instance().extract_feature(png)
    results = np.squeeze(results)
    train_vectors.append(results)


# In[78]:


testfiles = []

testfiles = glob.glob(os.path.join(os.path.join('test'), "*.mp4"))
sorted(testfiles)
count = 0
for each in testfiles:
    frameExtractor(each, os.path.join(os.getcwd(), "frames"), count)
    count += 1

frames_path = os.path.join(os.getcwd(), "frames")
files = []
files = glob.glob(os.path.join(frames_path, "*.png"))
sorted(files)
test_vectors = []
images=[]
new_count=0
for each in files:
    if new_count==count:
        break
    png = cv2.imread(each)
    png = cv2.cvtColor(png, cv2.COLOR_BGR2GRAY)
    results = HandShapeFeatureExtractor.get_instance().extract_feature(png)
    results = np.squeeze(results)
    test_vectors.append(results)
    images.append(os.path.basename(each))
    new_count +=1

print(images)


# In[79]:


res=[]
for each in test_vectors:
    lst = []
    for eachin in train_vectors:
        lst.append(dot(each, eachin)/(norm(each)*norm(eachin)))
        gesture_num = lst.index(min(lst))
        gesture_num = gesture_num
    res.append(gesture_num)
print(res)
np.savetxt("Results.csv", res, delimiter=",", fmt='% d')


# In[80]:





# In[ ]:





# In[ ]:




