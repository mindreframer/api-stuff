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
package com.wordnik.api.client.api

import com.wordnik.api.client.model._

import org.codehaus.jackson.map.DeserializationConfig.Feature
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.`type`.TypeReference
import com.wordnik.swagger.runtime.annotations._
import com.wordnik.swagger.runtime.common._
import com.wordnik.swagger.runtime.exception._

import java.io.IOException

import scala.collection.mutable._
import scala.collection.JavaConversions._


/**
 *
 * NOTE: This class is auto generated by the swagger code generator program. Do not edit the class manually.
 * @author tony
 *
 */
object WordAPI {

    /**
     * Returns examples for a word
     *
     * 
     * 
     * @param word  Word to return examples for
 @param includeDuplicates  Show duplicate examples from different sources
     *      Allowed values are - false,true  @param contentProvider  Return results from a specific ContentProvider
 @param useCanonical  If true will try to return the correct word root ('cats' -> 'cat'). If false returns exactly what was requested.
     *      Allowed values are - false,true  @param skip  Results to skip
 @param limit  Maximum number of results to return
 
     * @return ExampleSearchResults {@link ExampleSearchResults} 
     * @throws APIException 400 - Invalid word supplied. 
     */
     @MethodArgumentNames(value="word, includeDuplicates, contentProvider, useCanonical, skip, limit") 
     @throws(classOf[APIException])
     def getExamples(word:String, includeDuplicates:String, contentProvider:String, useCanonical:String, skip:String, limit:String) :ExampleSearchResults = {

        //parse inputs
        var resourcePath = "/word.{format}/{word}/examples".replace("{format}","json")
        val method = "GET";
        var queryParams = new HashMap[String, String]
        var headerParams = new HashMap[String, String]

        if(null != includeDuplicates) {
             queryParams += "includeDuplicates" -> APIInvoker.toPathValue(includeDuplicates)
        }
        if(null != contentProvider) {
             queryParams += "contentProvider" -> APIInvoker.toPathValue(contentProvider)
        }
        if(null != useCanonical) {
             queryParams += "useCanonical" -> APIInvoker.toPathValue(useCanonical)
        }
        if(null != skip) {
             queryParams += "skip" -> APIInvoker.toPathValue(skip)
        }
        if(null != limit) {
             queryParams += "limit" -> APIInvoker.toPathValue(limit)
        }

        if(null != word) {
            resourcePath = resourcePath.replace("{word}", APIInvoker.toPathValue(word))
        }
        
    
        //make the API Call
        val response = APIInvoker.getApiInvoker.invokeAPI(resourcePath, method, queryParams, null, headerParams)
        if(null == response || response.length() == 0){
            null
        }        //create output objects if the response has more than one object
        val responseObject = APIInvoker.deserialize(response, classOf[ExampleSearchResults]).asInstanceOf[ExampleSearchResults]
        responseObject        
                
     }
    /**
     * Given a word as a string, returns the WordObject that represents it
     *
     * 
     * 
     * @param word  String value of WordObject to return
 @param useCanonical  If true will try to return the correct word root ('cats' -> 'cat'). If false returns exactly what was requested.
     *      Allowed values are - false,true  @param includeSuggestions  Return suggestions (for correct spelling, case variants, etc.)
     *      Allowed values are - false,true  
     * @return WordObject {@link WordObject} 
     * @throws APIException 400 - Invalid word supplied. 
     */
     @MethodArgumentNames(value="word, useCanonical, includeSuggestions") 
     @throws(classOf[APIException])
     def getWord(word:String, useCanonical:String, includeSuggestions:String) :WordObject = {

        //parse inputs
        var resourcePath = "/word.{format}/{word}".replace("{format}","json")
        val method = "GET";
        var queryParams = new HashMap[String, String]
        var headerParams = new HashMap[String, String]

        if(null != useCanonical) {
             queryParams += "useCanonical" -> APIInvoker.toPathValue(useCanonical)
        }
        if(null != includeSuggestions) {
             queryParams += "includeSuggestions" -> APIInvoker.toPathValue(includeSuggestions)
        }

        if(null != word) {
            resourcePath = resourcePath.replace("{word}", APIInvoker.toPathValue(word))
        }
        
    
        //make the API Call
        val response = APIInvoker.getApiInvoker.invokeAPI(resourcePath, method, queryParams, null, headerParams)
        if(null == response || response.length() == 0){
            null
        }        //create output objects if the response has more than one object
        val responseObject = APIInvoker.deserialize(response, classOf[WordObject]).asInstanceOf[WordObject]
        responseObject        
                
     }
    /**
     * Return definitions for a word
     *
     * 
     * 
     * @param word  Word to return definitions for
 @param limit  Maximum number of results to return
 @param partOfSpeech  CSV list of part-of-speech types
     *      Allowed values are - noun,adjective,verb,adverb,interjection,pronoun,preposition,abbreviation,affix,article,auxiliary-verb,conjunction,definite-article,family-name,given-name,idiom,imperative,noun-plural,noun-posessive,past-participle,phrasal-prefix,proper-noun,proper-noun-plural,proper-noun-posessive,suffix,verb-intransitive,verb-transitive  @param includeRelated  Return related words with definitions
     *      Allowed values are - true,false  @param sourceDictionaries  If 'all' is received, results are returned from all sources. If multiple values are received (e.g. 'century,wiktionary'), results are returned from the first specified dictionary that has definitions. If left blank, results are returned from the first dictionary that has definitions. By default, dictionaries are searched in this order: ahd, wiktionary, webster, century, wordnet
     *      Allowed values are - all,ahd,century,wiktionary,webster,wordnet  @param useCanonical  If true will try to return the correct word root ('cats' -> 'cat'). If false returns exactly what was requested.
     *      Allowed values are - false,true  @param includeTags  Return a closed set of XML tags in response
     *      Allowed values are - false,true  
     * @return List[Definition] {@link Definition} 
     * @throws APIException 400 - Invalid word supplied. 404 - No definitions found. 
     */
     @MethodArgumentNames(value="word, limit, partOfSpeech, includeRelated, sourceDictionaries, useCanonical, includeTags") 
     @throws(classOf[APIException])
     def getDefinitions(word:String, limit:String, partOfSpeech:String, includeRelated:String, sourceDictionaries:List[String], useCanonical:String, includeTags:String) :List[Definition] = {

        //parse inputs
        var resourcePath = "/word.{format}/{word}/definitions".replace("{format}","json")
        val method = "GET";
        var queryParams = new HashMap[String, String]
        var headerParams = new HashMap[String, String]

        if(null != limit) {
             queryParams += "limit" -> APIInvoker.toPathValue(limit)
        }
        if(null != partOfSpeech) {
             queryParams += "partOfSpeech" -> APIInvoker.toPathValue(partOfSpeech)
        }
        if(null != includeRelated) {
             queryParams += "includeRelated" -> APIInvoker.toPathValue(includeRelated)
        }
        if(null != sourceDictionaries) {
             queryParams += "sourceDictionaries" -> APIInvoker.toPathValue(sourceDictionaries)
        }
        if(null != useCanonical) {
             queryParams += "useCanonical" -> APIInvoker.toPathValue(useCanonical)
        }
        if(null != includeTags) {
             queryParams += "includeTags" -> APIInvoker.toPathValue(includeTags)
        }

        if(null != word) {
            resourcePath = resourcePath.replace("{word}", APIInvoker.toPathValue(word))
        }
        
    
        //make the API Call
        val response = APIInvoker.getApiInvoker.invokeAPI(resourcePath, method, queryParams, null, headerParams)
        if(null == response || response.length() == 0){
            null
        }        
        val typeRef = new TypeReference[Array[Definition]] {}
        try {
            val responseObject = APIInvoker.mapper.readValue(response, typeRef).asInstanceOf[Array[Definition]]
            responseObject.toList
        } catch { 
        	case ioe:IOException => {
	            val args = Array(response, typeRef.toString())
	            throw new APIException(APIExceptionCodes.ERROR_CONVERTING_JSON_TO_JAVA, args, "Error in converting response json value to java object : " + ioe.getMessage(), ioe)
	        }
	        case _ => throw new APIException(APIExceptionCodes.ERROR_CONVERTING_JSON_TO_JAVA, "Error in converting response json value to java object")
        }                        
     }
    /**
     * Returns a top example for a word
     *
     * 
     * 
     * @param word  Word to fetch examples for
 @param contentProvider  Return results from a specific ContentProvider
 @param useCanonical  If true will try to return the correct word root ('cats' -> 'cat'). If false returns exactly what was requested.
     *      Allowed values are - false,true  
     * @return Example {@link Example} 
     * @throws APIException 400 - Invalid word supplied. 
     */
     @MethodArgumentNames(value="word, contentProvider, useCanonical") 
     @throws(classOf[APIException])
     def getTopExample(word:String, contentProvider:String, useCanonical:String) :Example = {

        //parse inputs
        var resourcePath = "/word.{format}/{word}/topExample".replace("{format}","json")
        val method = "GET";
        var queryParams = new HashMap[String, String]
        var headerParams = new HashMap[String, String]

        if(null != contentProvider) {
             queryParams += "contentProvider" -> APIInvoker.toPathValue(contentProvider)
        }
        if(null != useCanonical) {
             queryParams += "useCanonical" -> APIInvoker.toPathValue(useCanonical)
        }

        if(null != word) {
            resourcePath = resourcePath.replace("{word}", APIInvoker.toPathValue(word))
        }
        
    
        //make the API Call
        val response = APIInvoker.getApiInvoker.invokeAPI(resourcePath, method, queryParams, null, headerParams)
        if(null == response || response.length() == 0){
            null
        }        //create output objects if the response has more than one object
        val responseObject = APIInvoker.deserialize(response, classOf[Example]).asInstanceOf[Example]
        responseObject        
                
     }
    /**
     * Returns text pronunciations for a given word
     *
     * 
     * 
     * @param word  Word to get pronunciations for
 @param useCanonical  If true will try to return a correct word root ('cats' -> 'cat'). If false returns exactly what was requested.
     *      Allowed values are - false,true  @param sourceDictionary  Get from a single dictionary.
     *      Allowed values are - ahd,century,cmu,macmillan,wiktionary,webster,wordnet  @param typeFormat  Text pronunciation type
     *      Allowed values are - ahd,arpabet,gcide-diacritical,IPA  @param limit  Maximum number of results to return
 
     * @return List[TextPron] {@link TextPron} 
     * @throws APIException 400 - Invalid word supplied. 
     */
     @MethodArgumentNames(value="word, useCanonical, sourceDictionary, typeFormat, limit") 
     @throws(classOf[APIException])
     def getTextPronunciations(word:String, useCanonical:String, sourceDictionary:String, typeFormat:String, limit:String) :List[TextPron] = {

        //parse inputs
        var resourcePath = "/word.{format}/{word}/pronunciations".replace("{format}","json")
        val method = "GET";
        var queryParams = new HashMap[String, String]
        var headerParams = new HashMap[String, String]

        if(null != useCanonical) {
             queryParams += "useCanonical" -> APIInvoker.toPathValue(useCanonical)
        }
        if(null != sourceDictionary) {
             queryParams += "sourceDictionary" -> APIInvoker.toPathValue(sourceDictionary)
        }
        if(null != typeFormat) {
             queryParams += "typeFormat" -> APIInvoker.toPathValue(typeFormat)
        }
        if(null != limit) {
             queryParams += "limit" -> APIInvoker.toPathValue(limit)
        }

        if(null != word) {
            resourcePath = resourcePath.replace("{word}", APIInvoker.toPathValue(word))
        }
        
    
        //make the API Call
        val response = APIInvoker.getApiInvoker.invokeAPI(resourcePath, method, queryParams, null, headerParams)
        if(null == response || response.length() == 0){
            null
        }        
        val typeRef = new TypeReference[Array[TextPron]] {}
        try {
            val responseObject = APIInvoker.mapper.readValue(response, typeRef).asInstanceOf[Array[TextPron]]
            responseObject.toList
        } catch { 
        	case ioe:IOException => {
	            val args = Array(response, typeRef.toString())
	            throw new APIException(APIExceptionCodes.ERROR_CONVERTING_JSON_TO_JAVA, args, "Error in converting response json value to java object : " + ioe.getMessage(), ioe)
	        }
	        case _ => throw new APIException(APIExceptionCodes.ERROR_CONVERTING_JSON_TO_JAVA, "Error in converting response json value to java object")
        }                        
     }
    /**
     * Returns syllable information for a word
     *
     * 
     * 
     * @param word  Word to get syllables for
 @param useCanonical  If true will try to return a correct word root ('cats' -> 'cat'). If false returns exactly what was requested.
 @param sourceDictionary  Get from a single dictionary. Valid options: ahd, century, wiktionary, webster, and wordnet.
 @param limit  Maximum number of results to return
 
     * @return List[Syllable] {@link Syllable} 
     * @throws APIException 400 - Invalid word supplied. 
     */
     @MethodArgumentNames(value="word, useCanonical, sourceDictionary, limit") 
     @throws(classOf[APIException])
     def getHyphenation(word:String, useCanonical:String, sourceDictionary:String, limit:String) :List[Syllable] = {

        //parse inputs
        var resourcePath = "/word.{format}/{word}/hyphenation".replace("{format}","json")
        val method = "GET";
        var queryParams = new HashMap[String, String]
        var headerParams = new HashMap[String, String]

        if(null != useCanonical) {
             queryParams += "useCanonical" -> APIInvoker.toPathValue(useCanonical)
        }
        if(null != sourceDictionary) {
             queryParams += "sourceDictionary" -> APIInvoker.toPathValue(sourceDictionary)
        }
        if(null != limit) {
             queryParams += "limit" -> APIInvoker.toPathValue(limit)
        }

        if(null != word) {
            resourcePath = resourcePath.replace("{word}", APIInvoker.toPathValue(word))
        }
        
    
        //make the API Call
        val response = APIInvoker.getApiInvoker.invokeAPI(resourcePath, method, queryParams, null, headerParams)
        if(null == response || response.length() == 0){
            null
        }        
        val typeRef = new TypeReference[Array[Syllable]] {}
        try {
            val responseObject = APIInvoker.mapper.readValue(response, typeRef).asInstanceOf[Array[Syllable]]
            responseObject.toList
        } catch { 
        	case ioe:IOException => {
	            val args = Array(response, typeRef.toString())
	            throw new APIException(APIExceptionCodes.ERROR_CONVERTING_JSON_TO_JAVA, args, "Error in converting response json value to java object : " + ioe.getMessage(), ioe)
	        }
	        case _ => throw new APIException(APIExceptionCodes.ERROR_CONVERTING_JSON_TO_JAVA, "Error in converting response json value to java object")
        }                        
     }
    /**
     * Returns word usage over time
     *
     * 
     * 
     * @param word  Word to return
 @param useCanonical  If true will try to return the correct word root ('cats' -> 'cat'). If false returns exactly what was requested.
 @param startYear  Starting Year
 @param endYear  Ending Year
 
     * @return FrequencySummary {@link FrequencySummary} 
     * @throws APIException 400 - Invalid word supplied. 404 - No results. 
     */
     @MethodArgumentNames(value="word, useCanonical, startYear, endYear") 
     @throws(classOf[APIException])
     def getWordFrequency(word:String, useCanonical:String, startYear:String, endYear:String) :FrequencySummary = {

        //parse inputs
        var resourcePath = "/word.{format}/{word}/frequency".replace("{format}","json")
        val method = "GET";
        var queryParams = new HashMap[String, String]
        var headerParams = new HashMap[String, String]

        if(null != useCanonical) {
             queryParams += "useCanonical" -> APIInvoker.toPathValue(useCanonical)
        }
        if(null != startYear) {
             queryParams += "startYear" -> APIInvoker.toPathValue(startYear)
        }
        if(null != endYear) {
             queryParams += "endYear" -> APIInvoker.toPathValue(endYear)
        }

        if(null != word) {
            resourcePath = resourcePath.replace("{word}", APIInvoker.toPathValue(word))
        }
        
    
        //make the API Call
        val response = APIInvoker.getApiInvoker.invokeAPI(resourcePath, method, queryParams, null, headerParams)
        if(null == response || response.length() == 0){
            null
        }        //create output objects if the response has more than one object
        val responseObject = APIInvoker.deserialize(response, classOf[FrequencySummary]).asInstanceOf[FrequencySummary]
        responseObject        
                
     }
    /**
     * Fetches bi-gram phrases for a word
     *
     * 
     * 
     * @param word  Word to fetch phrases for
 @param limit  Maximum number of results to return
 @param wlmi  Minimum WLMI for the phrase
 @param useCanonical  If true will try to return the correct word root ('cats' -> 'cat'). If false returns exactly what was requested.
     *      Allowed values are - false,true  
     * @return List[Bigram] {@link Bigram} 
     * @throws APIException 400 - Invalid word supplied. 
     */
     @MethodArgumentNames(value="word, limit, wlmi, useCanonical") 
     @throws(classOf[APIException])
     def getPhrases(word:String, limit:String, wlmi:String, useCanonical:String) :List[Bigram] = {

        //parse inputs
        var resourcePath = "/word.{format}/{word}/phrases".replace("{format}","json")
        val method = "GET";
        var queryParams = new HashMap[String, String]
        var headerParams = new HashMap[String, String]

        if(null != limit) {
             queryParams += "limit" -> APIInvoker.toPathValue(limit)
        }
        if(null != wlmi) {
             queryParams += "wlmi" -> APIInvoker.toPathValue(wlmi)
        }
        if(null != useCanonical) {
             queryParams += "useCanonical" -> APIInvoker.toPathValue(useCanonical)
        }

        if(null != word) {
            resourcePath = resourcePath.replace("{word}", APIInvoker.toPathValue(word))
        }
        
    
        //make the API Call
        val response = APIInvoker.getApiInvoker.invokeAPI(resourcePath, method, queryParams, null, headerParams)
        if(null == response || response.length() == 0){
            null
        }        
        val typeRef = new TypeReference[Array[Bigram]] {}
        try {
            val responseObject = APIInvoker.mapper.readValue(response, typeRef).asInstanceOf[Array[Bigram]]
            responseObject.toList
        } catch { 
        	case ioe:IOException => {
	            val args = Array(response, typeRef.toString())
	            throw new APIException(APIExceptionCodes.ERROR_CONVERTING_JSON_TO_JAVA, args, "Error in converting response json value to java object : " + ioe.getMessage(), ioe)
	        }
	        case _ => throw new APIException(APIExceptionCodes.ERROR_CONVERTING_JSON_TO_JAVA, "Error in converting response json value to java object")
        }                        
     }
    /**
     * Return related words (thesaurus data) for a word
     *
     * 
     * 
     * @param word  Word for which to return related words
 @param partOfSpeech  CSV list of part-of-speech types
     *      Allowed values are - noun,adjective,verb,adverb,interjection,pronoun,preposition,abbreviation,affix,article,auxiliary-verb,conjunction,definite-article,family-name,given-name,idiom,imperative,noun-plural,noun-posessive,past-participle,phrasal-prefix,proper-noun,proper-noun-plural,proper-noun-posessive,suffix,verb-intransitive,verb-transitive  @param sourceDictionary  Get data from a single dictionary
     *      Allowed values are - ahd, century, wiktionary, webster, wordnet  @param limit  Maximum number of results to return
 @param useCanonical  If true will try to return the correct word root ('cats' -> 'cat'). If false returns exactly what was requested.
     *      Allowed values are - false,true  @param type  Relationship type
     *      Allowed values are - synonym,antonym,variant,equivalent,cross-reference,related-word,rhyme,form,etymologically-related-term,hypernym,hyponym,inflected-form,primary,same-context,verb-form,verb-stem,unknown  
     * @return List[Related] {@link Related} 
     * @throws APIException 400 - Invalid word supplied. 404 - No definitions found. 
     */
     @MethodArgumentNames(value="word, partOfSpeech, sourceDictionary, limit, useCanonical, type") 
     @throws(classOf[APIException])
     def getRelated(word:String, partOfSpeech:String, sourceDictionary:String, limit:String, useCanonical:String, `type`:String) :List[Related] = {

        //parse inputs
        var resourcePath = "/word.{format}/{word}/related".replace("{format}","json")
        val method = "GET";
        var queryParams = new HashMap[String, String]
        var headerParams = new HashMap[String, String]

        if(null != partOfSpeech) {
             queryParams += "partOfSpeech" -> APIInvoker.toPathValue(partOfSpeech)
        }
        if(null != sourceDictionary) {
             queryParams += "sourceDictionary" -> APIInvoker.toPathValue(sourceDictionary)
        }
        if(null != limit) {
             queryParams += "limit" -> APIInvoker.toPathValue(limit)
        }
        if(null != useCanonical) {
             queryParams += "useCanonical" -> APIInvoker.toPathValue(useCanonical)
        }
        if(null != `type`) {
             queryParams += "type" -> APIInvoker.toPathValue(`type`)
        }

        if(null != word) {
            resourcePath = resourcePath.replace("{word}", APIInvoker.toPathValue(word))
        }
        
    
        //make the API Call
        val response = APIInvoker.getApiInvoker.invokeAPI(resourcePath, method, queryParams, null, headerParams)
        if(null == response || response.length() == 0){
            null
        }        
        val typeRef = new TypeReference[Array[Related]] {}
        try {
            val responseObject = APIInvoker.mapper.readValue(response, typeRef).asInstanceOf[Array[Related]]
            responseObject.toList
        } catch { 
        	case ioe:IOException => {
	            val args = Array(response, typeRef.toString())
	            throw new APIException(APIExceptionCodes.ERROR_CONVERTING_JSON_TO_JAVA, args, "Error in converting response json value to java object : " + ioe.getMessage(), ioe)
	        }
	        case _ => throw new APIException(APIExceptionCodes.ERROR_CONVERTING_JSON_TO_JAVA, "Error in converting response json value to java object")
        }                        
     }
    /**
     * Fetches audio metadata for a word.
     *
     * The metadata includes a time-expiring fileUrl which allows reading the audio file directly from the API.  Currently only audio pronunciations from the American Heritage Dictionary in mp3 format are supported.
     * 
     * @param word  Word to get audio for.
 @param useCanonical  Use the canonical form of the word.
 @param limit  Maximum number of results to return
 
     * @return List[AudioFile] {@link AudioFile} 
     * @throws APIException 400 - Invalid word supplied. 
     */
     @MethodArgumentNames(value="word, useCanonical, limit") 
     @throws(classOf[APIException])
     def getAudio(word:String, useCanonical:String, limit:String) :List[AudioFile] = {

        //parse inputs
        var resourcePath = "/word.{format}/{word}/audio".replace("{format}","json")
        val method = "GET";
        var queryParams = new HashMap[String, String]
        var headerParams = new HashMap[String, String]

        if(null != useCanonical) {
             queryParams += "useCanonical" -> APIInvoker.toPathValue(useCanonical)
        }
        if(null != limit) {
             queryParams += "limit" -> APIInvoker.toPathValue(limit)
        }

        if(null != word) {
            resourcePath = resourcePath.replace("{word}", APIInvoker.toPathValue(word))
        }
        
    
        //make the API Call
        val response = APIInvoker.getApiInvoker.invokeAPI(resourcePath, method, queryParams, null, headerParams)
        if(null == response || response.length() == 0){
            null
        }        
        val typeRef = new TypeReference[Array[AudioFile]] {}
        try {
            val responseObject = APIInvoker.mapper.readValue(response, typeRef).asInstanceOf[Array[AudioFile]]
            responseObject.toList
        } catch { 
        	case ioe:IOException => {
	            val args = Array(response, typeRef.toString())
	            throw new APIException(APIExceptionCodes.ERROR_CONVERTING_JSON_TO_JAVA, args, "Error in converting response json value to java object : " + ioe.getMessage(), ioe)
	        }
	        case _ => throw new APIException(APIExceptionCodes.ERROR_CONVERTING_JSON_TO_JAVA, "Error in converting response json value to java object")
        }                        
     }

}