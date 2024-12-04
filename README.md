# Search Result Clustering

## Table of Contents
1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Prerequisites](#prerequisites)
5. [How to Run](#how-to-run)
6. [Contributors](#contributors)

---

## Project Overview

This project implements **Search Result Clustering**, showcasing a cluster-based representation of search results retrieved for a given query. The application uses **Apache Lucene** for document indexing and searching, and **K-Means clustering** for organizing search results into meaningful clusters. The clusters are visualized using **Apexcharts Scatter Charts** in a user-friendly Angular-based front-end interface.

---

## Features

- **Custom Wikipedia Dataset**: A subset of 1000 English Wikipedia topics is pre-processed and indexed.
- **Search Functionality**: Retrieves relevant documents for any user query using Apache Lucene.
- **Clustering**: Clusters search results using K-Means on TF-IDF vectors.
- **Dimensionality Reduction**: Employs PCA for 2D visualization of clustered data.
- **Interactive Visualization**: Apexcharts Scatter Charts display clustered search results.

---

## Technologies Used

- **Backend**: 
  - Apache Lucene
  - Python (TF-IDF, K-Means, PCA via sklearn)

- **Frontend**: 
  - Angular Framework
  - Apexcharts

---

## Prerequisites

Ensure the following tools and versions are installed on your system:

- **Java**: 1.8.0_202
- **Python**: 3.9.13
- **Angular CLI**: 15.0.4
- **Node.js**: 18.12.1
- **npm**: 8.19.2
- **Operating System**: Windows 64-bit

---

## How to Run

1. **Extract Files**: Unzip the `Search-Engine.zip` file to your local system.
2. **Start the Backend**:
   - Navigate to the `backend` folder.
   - Open the project in Eclipse (or any preferred IDE).
   - Go to `src\main\java\com\searchEngine` and run `SearchEngineApplication.java` as a Java application. This starts the backend server.
3. **Set Up the Frontend**:
   - Navigate to `frontend/my-app` and open a command prompt in this directory.
   - Run the following command to install required dependencies:
     ```bash
     npm i
     ```
   - Start the Angular development server:
     ```bash
     ng serve --open
     ```
     The UI will open automatically in your default web browser.
4. **Use the Application**:
   - Enter a search term and specify the number of documents to retrieve (e.g., `India` and `20`).
   - Click "Go" to view clustered search results visualized on the scatter chart.

---
