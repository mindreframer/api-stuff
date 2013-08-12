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
package com.wordnik.swaggersocket.samples

import java.text.SimpleDateFormat

import org.apache.commons.lang.StringUtils
import java.util.Date


trait RestResourceUtil {
  def asOption(s: String): Option[String] = {
    StringUtils.isBlank(s) match {
      case true => None
      case _ => Some(s)
    }
  }

  def getInt(minVal: Int, maxVal: Int, defaultValue: Int, inputString: String): Int = {
    var output: Int = {
      if (null == inputString)
        defaultValue
      else {
        try inputString.toInt
        catch {
          case _ => defaultValue
        }
      }
    }

    if (output < minVal) output = minVal
    if (maxVal == -1) { if (output < minVal) output = minVal }
    else if (output > maxVal) output = maxVal
    output
  }

  def getLong(minVal: Long, maxVal: Long, defaultValue: Long, inputString: String): Long = {
    var output: Long = defaultValue;
    try output = inputString.toLong
    catch {
      case _ => output = defaultValue
    }

    if (output < minVal) output = minVal
    if (maxVal == -1) { if (output < minVal) output = minVal }
    else if (output > maxVal) output = maxVal
    output
  }

  def getDouble(minVal: Double, maxVal: Double, defaultValue: Double, inputString: String): Double = {
    var output: Double = defaultValue;
    try output = inputString.toDouble
    catch {
      case _ => output = defaultValue
    }

    if (output < minVal) output = minVal
    if (maxVal == -1) { if (output < minVal) output = minVal }
    else if (output > maxVal) output = maxVal
    output
  }

  def getBoolean(defaultValue: Boolean, booleanString: String): Boolean = {
    var output: Boolean = defaultValue
    if (booleanString == null) output = defaultValue

    //	treat "", "YES" as "true"
    if ("".equals(booleanString)) output = true
    else if ("YES".equalsIgnoreCase(booleanString)) output = true
    else if ("NO".equalsIgnoreCase(booleanString)) output = false
    else {
      try output = booleanString.toBoolean
      catch {
        case _ => output = defaultValue
      }
    }
    output
  }

  def getDate(defaultValue: Date, dateString: String): Date = {
    try new SimpleDateFormat("yyyy-MM-dd").parse(dateString)
    catch {
      case _ => defaultValue
    }
  }
}