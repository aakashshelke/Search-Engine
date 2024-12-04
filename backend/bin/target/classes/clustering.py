import pandas as pd
import json
import matplotlib.pyplot as plt
from sklearn.cluster import KMeans
from sklearn.feature_extraction.text import TfidfVectorizer

with open('src/main/resources/searchResult.json', 'r', encoding='utf-8') as f:
  # Load the JSON data into a Python object
  response = json.load(f)

response_df = pd.DataFrame(response)
try:
  wiki_lst = response_df['content']
  title = response_df['title']
  score = response_df['score']

  vectorizer = TfidfVectorizer(stop_words={'english'})
  X = vectorizer.fit_transform(wiki_lst)

  if (len(wiki_lst)<4):
    true_k = len(wiki_lst)
  else:
    true_k = 4
  model = KMeans(n_clusters=true_k, init='k-means++', max_iter=200, n_init=10)
  model.fit(X)
  labels=model.labels_
  # wiki_cl=pd.DataFrame(list(zip(title,score,labels)),columns=['name','value','cluster'])
  response_df['cluster'] = labels

  response_dict = response_df.to_dict('records')

  response_list = []
  for index, group in response_df.groupby('cluster'):
      response_list.append(group.to_dict('records'))

  with open("src/main/resources/clusters.json", "w") as outfile:
      # Write the data to the file
      json.dump(response_list, outfile)
except Exception as e:
  print(e)
  with open("src/main/resources/clusters.json", "w") as outfile:
      # Write the data to the file
      json.dump([], outfile)