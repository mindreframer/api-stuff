/**
 *  Copyright 2012 Wordnik, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
 
package com.wordnik.api.client.model

import com.wordnik.swagger.runtime.annotations._

import scala.reflect.BeanProperty

import scala.collection.JavaConversions._

import scala.collection.mutable.ListBuffer


/**
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program. Do not edit the class manually.
 *
 * @author tony
 *
 */
class TestData extends Object {

	/**
	 * 
	 * 
	 * 
	 */
	var apiTokenStatusList  =  new ListBuffer[ApiTokenStatus]
	def getApiTokenStatusList:java.util.List[com.wordnik.api.client.model.ApiTokenStatus] = {
	    apiTokenStatusList.toList
	}
	def setApiTokenStatusList(args:java.util.List[com.wordnik.api.client.model.ApiTokenStatus]) = {
	    apiTokenStatusList.clear
	    args.foreach(arg=>apiTokenStatusList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var userList  =  new ListBuffer[User]
	def getUserList:java.util.List[com.wordnik.api.client.model.User] = {
	    userList.toList
	}
	def setUserList(args:java.util.List[com.wordnik.api.client.model.User]) = {
	    userList.clear
	    args.foreach(arg=>userList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var authenticationTokenList  =  new ListBuffer[AuthenticationToken]
	def getAuthenticationTokenList:java.util.List[com.wordnik.api.client.model.AuthenticationToken] = {
	    authenticationTokenList.toList
	}
	def setAuthenticationTokenList(args:java.util.List[com.wordnik.api.client.model.AuthenticationToken]) = {
	    authenticationTokenList.clear
	    args.foreach(arg=>authenticationTokenList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var wordListList  =  new ListBuffer[WordList]
	def getWordListList:java.util.List[com.wordnik.api.client.model.WordList] = {
	    wordListList.toList
	}
	def setWordListList(args:java.util.List[com.wordnik.api.client.model.WordList]) = {
	    wordListList.clear
	    args.foreach(arg=>wordListList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var syllableList  =  new ListBuffer[Syllable]
	def getSyllableList:java.util.List[com.wordnik.api.client.model.Syllable] = {
	    syllableList.toList
	}
	def setSyllableList(args:java.util.List[com.wordnik.api.client.model.Syllable]) = {
	    syllableList.clear
	    args.foreach(arg=>syllableList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var audioTypeList  =  new ListBuffer[AudioType]
	def getAudioTypeList:java.util.List[com.wordnik.api.client.model.AudioType] = {
	    audioTypeList.toList
	}
	def setAudioTypeList(args:java.util.List[com.wordnik.api.client.model.AudioType]) = {
	    audioTypeList.clear
	    args.foreach(arg=>audioTypeList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var facetList  =  new ListBuffer[Facet]
	def getFacetList:java.util.List[com.wordnik.api.client.model.Facet] = {
	    facetList.toList
	}
	def setFacetList(args:java.util.List[com.wordnik.api.client.model.Facet]) = {
	    facetList.clear
	    args.foreach(arg=>facetList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var noteList  =  new ListBuffer[Note]
	def getNoteList:java.util.List[com.wordnik.api.client.model.Note] = {
	    noteList.toList
	}
	def setNoteList(args:java.util.List[com.wordnik.api.client.model.Note]) = {
	    noteList.clear
	    args.foreach(arg=>noteList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var facetValueList  =  new ListBuffer[FacetValue]
	def getFacetValueList:java.util.List[com.wordnik.api.client.model.FacetValue] = {
	    facetValueList.toList
	}
	def setFacetValueList(args:java.util.List[com.wordnik.api.client.model.FacetValue]) = {
	    facetValueList.clear
	    args.foreach(arg=>facetValueList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var wordObjectList  =  new ListBuffer[WordObject]
	def getWordObjectList:java.util.List[com.wordnik.api.client.model.WordObject] = {
	    wordObjectList.toList
	}
	def setWordObjectList(args:java.util.List[com.wordnik.api.client.model.WordObject]) = {
	    wordObjectList.clear
	    args.foreach(arg=>wordObjectList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var relatedList  =  new ListBuffer[Related]
	def getRelatedList:java.util.List[com.wordnik.api.client.model.Related] = {
	    relatedList.toList
	}
	def setRelatedList(args:java.util.List[com.wordnik.api.client.model.Related]) = {
	    relatedList.clear
	    args.foreach(arg=>relatedList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var citationList  =  new ListBuffer[Citation]
	def getCitationList:java.util.List[com.wordnik.api.client.model.Citation] = {
	    citationList.toList
	}
	def setCitationList(args:java.util.List[com.wordnik.api.client.model.Citation]) = {
	    citationList.clear
	    args.foreach(arg=>citationList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var scoredWordList  =  new ListBuffer[ScoredWord]
	def getScoredWordList:java.util.List[com.wordnik.api.client.model.ScoredWord] = {
	    scoredWordList.toList
	}
	def setScoredWordList(args:java.util.List[com.wordnik.api.client.model.ScoredWord]) = {
	    scoredWordList.clear
	    args.foreach(arg=>scoredWordList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var exampleSearchResultsList  =  new ListBuffer[ExampleSearchResults]
	def getExampleSearchResultsList:java.util.List[com.wordnik.api.client.model.ExampleSearchResults] = {
	    exampleSearchResultsList.toList
	}
	def setExampleSearchResultsList(args:java.util.List[com.wordnik.api.client.model.ExampleSearchResults]) = {
	    exampleSearchResultsList.clear
	    args.foreach(arg=>exampleSearchResultsList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var categoryList  =  new ListBuffer[Category]
	def getCategoryList:java.util.List[com.wordnik.api.client.model.Category] = {
	    categoryList.toList
	}
	def setCategoryList(args:java.util.List[com.wordnik.api.client.model.Category]) = {
	    categoryList.clear
	    args.foreach(arg=>categoryList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var exampleList  =  new ListBuffer[Example]
	def getExampleList:java.util.List[com.wordnik.api.client.model.Example] = {
	    exampleList.toList
	}
	def setExampleList(args:java.util.List[com.wordnik.api.client.model.Example]) = {
	    exampleList.clear
	    args.foreach(arg=>exampleList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var sentenceList  =  new ListBuffer[Sentence]
	def getSentenceList:java.util.List[com.wordnik.api.client.model.Sentence] = {
	    sentenceList.toList
	}
	def setSentenceList(args:java.util.List[com.wordnik.api.client.model.Sentence]) = {
	    sentenceList.clear
	    args.foreach(arg=>sentenceList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var rootList  =  new ListBuffer[Root]
	def getRootList:java.util.List[com.wordnik.api.client.model.Root] = {
	    rootList.toList
	}
	def setRootList(args:java.util.List[com.wordnik.api.client.model.Root]) = {
	    rootList.clear
	    args.foreach(arg=>rootList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var audioFileList  =  new ListBuffer[AudioFile]
	def getAudioFileList:java.util.List[com.wordnik.api.client.model.AudioFile] = {
	    audioFileList.toList
	}
	def setAudioFileList(args:java.util.List[com.wordnik.api.client.model.AudioFile]) = {
	    audioFileList.clear
	    args.foreach(arg=>audioFileList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var contentProviderList  =  new ListBuffer[ContentProvider]
	def getContentProviderList:java.util.List[com.wordnik.api.client.model.ContentProvider] = {
	    contentProviderList.toList
	}
	def setContentProviderList(args:java.util.List[com.wordnik.api.client.model.ContentProvider]) = {
	    contentProviderList.clear
	    args.foreach(arg=>contentProviderList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var exampleUsageList  =  new ListBuffer[ExampleUsage]
	def getExampleUsageList:java.util.List[com.wordnik.api.client.model.ExampleUsage] = {
	    exampleUsageList.toList
	}
	def setExampleUsageList(args:java.util.List[com.wordnik.api.client.model.ExampleUsage]) = {
	    exampleUsageList.clear
	    args.foreach(arg=>exampleUsageList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var bigramList  =  new ListBuffer[Bigram]
	def getBigramList:java.util.List[com.wordnik.api.client.model.Bigram] = {
	    bigramList.toList
	}
	def setBigramList(args:java.util.List[com.wordnik.api.client.model.Bigram]) = {
	    bigramList.clear
	    args.foreach(arg=>bigramList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var labelList  =  new ListBuffer[Label]
	def getLabelList:java.util.List[com.wordnik.api.client.model.Label] = {
	    labelList.toList
	}
	def setLabelList(args:java.util.List[com.wordnik.api.client.model.Label]) = {
	    labelList.clear
	    args.foreach(arg=>labelList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var frequencyList  =  new ListBuffer[Frequency]
	def getFrequencyList:java.util.List[com.wordnik.api.client.model.Frequency] = {
	    frequencyList.toList
	}
	def setFrequencyList(args:java.util.List[com.wordnik.api.client.model.Frequency]) = {
	    frequencyList.clear
	    args.foreach(arg=>frequencyList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var partOfSpeechList  =  new ListBuffer[PartOfSpeech]
	def getPartOfSpeechList:java.util.List[com.wordnik.api.client.model.PartOfSpeech] = {
	    partOfSpeechList.toList
	}
	def setPartOfSpeechList(args:java.util.List[com.wordnik.api.client.model.PartOfSpeech]) = {
	    partOfSpeechList.clear
	    args.foreach(arg=>partOfSpeechList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var definitionList  =  new ListBuffer[Definition]
	def getDefinitionList:java.util.List[com.wordnik.api.client.model.Definition] = {
	    definitionList.toList
	}
	def setDefinitionList(args:java.util.List[com.wordnik.api.client.model.Definition]) = {
	    definitionList.clear
	    args.foreach(arg=>definitionList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var textPronList  =  new ListBuffer[TextPron]
	def getTextPronList:java.util.List[com.wordnik.api.client.model.TextPron] = {
	    textPronList.toList
	}
	def setTextPronList(args:java.util.List[com.wordnik.api.client.model.TextPron]) = {
	    textPronList.clear
	    args.foreach(arg=>textPronList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var frequencySummaryList  =  new ListBuffer[FrequencySummary]
	def getFrequencySummaryList:java.util.List[com.wordnik.api.client.model.FrequencySummary] = {
	    frequencySummaryList.toList
	}
	def setFrequencySummaryList(args:java.util.List[com.wordnik.api.client.model.FrequencySummary]) = {
	    frequencySummaryList.clear
	    args.foreach(arg=>frequencySummaryList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var wordListWordList  =  new ListBuffer[WordListWord]
	def getWordListWordList:java.util.List[com.wordnik.api.client.model.WordListWord] = {
	    wordListWordList.toList
	}
	def setWordListWordList(args:java.util.List[com.wordnik.api.client.model.WordListWord]) = {
	    wordListWordList.clear
	    args.foreach(arg=>wordListWordList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var wordSearchResultsList  =  new ListBuffer[WordSearchResults]
	def getWordSearchResultsList:java.util.List[com.wordnik.api.client.model.WordSearchResults] = {
	    wordSearchResultsList.toList
	}
	def setWordSearchResultsList(args:java.util.List[com.wordnik.api.client.model.WordSearchResults]) = {
	    wordSearchResultsList.clear
	    args.foreach(arg=>wordSearchResultsList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var wordSearchResultList  =  new ListBuffer[WordSearchResult]
	def getWordSearchResultList:java.util.List[com.wordnik.api.client.model.WordSearchResult] = {
	    wordSearchResultList.toList
	}
	def setWordSearchResultList(args:java.util.List[com.wordnik.api.client.model.WordSearchResult]) = {
	    wordSearchResultList.clear
	    args.foreach(arg=>wordSearchResultList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var wordFrequencyList  =  new ListBuffer[WordFrequency]
	def getWordFrequencyList:java.util.List[com.wordnik.api.client.model.WordFrequency] = {
	    wordFrequencyList.toList
	}
	def setWordFrequencyList(args:java.util.List[com.wordnik.api.client.model.WordFrequency]) = {
	    wordFrequencyList.clear
	    args.foreach(arg=>wordFrequencyList += arg)
	}

	/**
	 * 
	 * 
	 * 
	 */
	var wordOfTheDayList  =  new ListBuffer[WordOfTheDay]
	def getWordOfTheDayList:java.util.List[com.wordnik.api.client.model.WordOfTheDay] = {
	    wordOfTheDayList.toList
	}
	def setWordOfTheDayList(args:java.util.List[com.wordnik.api.client.model.WordOfTheDay]) = {
	    wordOfTheDayList.clear
	    args.foreach(arg=>wordOfTheDayList += arg)
	}

    override def toString:String = {
        "[" +
        "apiTokenStatusList:" + apiTokenStatusList + 
            "userList:" + userList + 
            "authenticationTokenList:" + authenticationTokenList + 
            "wordListList:" + wordListList + 
            "syllableList:" + syllableList + 
            "audioTypeList:" + audioTypeList + 
            "facetList:" + facetList + 
            "noteList:" + noteList + 
            "facetValueList:" + facetValueList + 
            "wordObjectList:" + wordObjectList + 
            "relatedList:" + relatedList + 
            "citationList:" + citationList + 
            "scoredWordList:" + scoredWordList + 
            "exampleSearchResultsList:" + exampleSearchResultsList + 
            "categoryList:" + categoryList + 
            "exampleList:" + exampleList + 
            "sentenceList:" + sentenceList + 
            "rootList:" + rootList + 
            "audioFileList:" + audioFileList + 
            "contentProviderList:" + contentProviderList + 
            "exampleUsageList:" + exampleUsageList + 
            "bigramList:" + bigramList + 
            "labelList:" + labelList + 
            "frequencyList:" + frequencyList + 
            "partOfSpeechList:" + partOfSpeechList + 
            "definitionList:" + definitionList + 
            "textPronList:" + textPronList + 
            "frequencySummaryList:" + frequencySummaryList + 
            "wordListWordList:" + wordListWordList + 
            "wordSearchResultsList:" + wordSearchResultsList + 
            "wordSearchResultList:" + wordSearchResultList + 
            "wordFrequencyList:" + wordFrequencyList + 
            "wordOfTheDayList:" + wordOfTheDayList + "]"
    }
}