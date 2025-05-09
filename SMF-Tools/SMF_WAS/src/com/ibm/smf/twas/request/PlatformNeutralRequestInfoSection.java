/*                                                                   */
/* Copyright 2025 IBM Corp.                                          */
/*                                                                   */
/* Licensed under the Apache License, Version 2.0 (the "License");   */
/* you may not use this file except in compliance with the License.  */
/* You may obtain a copy of the License at                           */
/*                                                                   */
/* http://www.apache.org/licenses/LICENSE-2.0                        */
/*                                                                   */
/* Unless required by applicable law or agreed to in writing,        */
/* software distributed under the License is distributed on an       */
/* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,      */
/* either express or implied. See the License for the specific       */
/* language governing permissions and limitations under the License. */
/*                                                                   */

  package com.ibm.smf.twas.request;

  import java.io.*;

import com.ibm.smf.format.SmfEntity;
import com.ibm.smf.format.SmfPrintStream;
import com.ibm.smf.format.SmfStream;
import com.ibm.smf.format.UnsupportedVersionException;

//  ------------------------------------------------------------------------------
  /** Data container for SMF data related to a Smf record product section. */
  public class PlatformNeutralRequestInfoSection extends SmfEntity {
     
    /** Supported version of this class. */
	  public final static int s_supportedVersion = 1;  
    /** version of this section */
    public int m_Version;
    /** process of ID of the servant where the request was dispatched */
    public byte m_dispatchServantPID[];
    /** task id of the thread in the servant where the request was dispatched */
    public byte m_dispatchTaskId[];
    /** CPU time from TCB where the request was dispatched */
    public long m_dispatchTcbCpu;
    /** If an uncaught system exception was thrown while processing the request the
     * resulting minor code is here
     */
    public byte m_completionMinorCode[];
    /** reserved space */
    public byte m_reserved[];
    /** type of the request (e.g. HTTP, IIOP, etc.) */
    public int m_requestType;
    /** reserved space */
    public byte m_theblob[];
    
    /** Unknown work type */
    static public final int TypeUnknown = 0;   
    /** IIOP work */
    static public final int TypeIIOP = 1;
    /** HTTP work */
    static public final int TypeHTTP = 2;
    /** HTTP SSL work */
    static public final int TypeHTTPS = 3;
    /** MDB Plan A work */
    static public final int TypeMDBA = 4;
    /** MDB Plan B work */
    static public final int TypeMDBB = 5;
    /** MDB Plan C - CRA work */
    static public final int TypeMDBC = 6;
    /** SIP work */
    static public final int TypeSIP = 7;
    /** SIP SSL work */
    static public final int TypeSIPS =8;
    /** MBean work */
    static public final int TypeMBean =9;
    /** OTS work */
    static public final int TypeOTS = 10;
    /** 'Other' work */
    static public final int TypeOther = 11;
    /** OLA work */
    static public final int TypeOLA = 12;
    
    /** max type value...update if we add more! */
    static public final int TypeMax=TypeOLA;
    
    //----------------------------------------------------------------------------
    /** PlatformNeutralRequestInfoSection constructor from a SmfStream.
     * @param aSmfStream SmfStream to be used to build this PlatformNeutralRequestInfoSection.
     * The requested version is currently set in the Platform Neutral Section
     * @throws UnsupportedVersionException Exception to be thrown when version is not supported
     * @throws UnsupportedEncodingException Exception thrown when an unsupported encoding is detected.
     */
    public PlatformNeutralRequestInfoSection(SmfStream aSmfStream) 
    throws UnsupportedVersionException, UnsupportedEncodingException {
      
      super(s_supportedVersion);
      // 68 total
      m_Version = aSmfStream.getInteger(4);   
      m_dispatchServantPID = aSmfStream.getByteBuffer(4);
      m_dispatchTaskId =  aSmfStream.getByteBuffer(8);
      m_dispatchTcbCpu =  aSmfStream.getLong();
      m_completionMinorCode = aSmfStream.getByteBuffer(4);
      m_reserved = aSmfStream.getByteBuffer(4);
      m_requestType = aSmfStream.getInteger(4);   
      m_theblob = aSmfStream.getByteBuffer(32);
      
    } // PlatformNeutralRequestInfoSection(..)
    
    //----------------------------------------------------------------------------
    /** Returns the supported version of this class.
     * @return supported version of this class.
     */
    public int supportedVersion() {
      
      return s_supportedVersion;
      
    } // supportedVersion()
    
    public String getRequestTypeString() {
    	switch (m_requestType) {
    	case TypeUnknown:
    		return "Unknown";
    	case TypeIIOP:
    		return "IIOP";
    	case TypeHTTP:
    		return "HTTP";
    	case TypeHTTPS:
    		return "HTTPS";
    	case TypeMDBA:
    		return "MDBA";
    	case TypeMDBB:
    		return "MDBB";
    	case TypeMDBC:
    		return "MDBC";
    	case TypeSIP:
    		return "SIP";
    	case TypeSIPS:
    		return "SIPS";
    	case TypeMBean:
    		return "MBean";
    	case TypeOTS:
    		return "OTS";
    	case TypeOther:
    		return "Other";
    	case TypeOLA:
    		return "OLA";
		default:
			return "MissingCase";
    	}
    }


    //----------------------------------------------------------------------------
    /** Dumps the fields of this object to a print stream.
     * @param aPrintStream The stream to print to.
     * @param aTripletNumber The triplet number of this PlatformNeutralRequestInfoSection.
     */
    public void dump(SmfPrintStream aPrintStream, int aTripletNumber) {

      // Append a user readable type string as a description
      // instead of just the number for the request types
      String requestTypeString = "";
      switch (m_requestType) {
        case 0 : requestTypeString = "UNKNOWN"; break;
        case 1 : requestTypeString = "IIOP"; break;
        case 2 : requestTypeString = "HTTP"; break;
        case 3 : requestTypeString = "HTTPS"; break;
        case 4 : requestTypeString = "MDB Plan \"A\""; break;
        case 5 : requestTypeString = "MDB Plan \"B\""; break;
        case 6 : requestTypeString = "MDB Plan \"C\""; break;
        case 7 : requestTypeString = "SIP"; break;
        case 8 : requestTypeString = "SIPS"; break;
        case 9 : requestTypeString = "MBEAN"; break;
        case 10 : requestTypeString = "OTS"; break;
        case 11 : requestTypeString = "Other Internal"; break;
        case 12 : requestTypeString = "OLA"; break;
      }
      
      aPrintStream.println("");
      aPrintStream.printKeyValue("Triplet #",aTripletNumber);
      aPrintStream.printlnKeyValue("Type","PlatformNeutralRequestInfoSection");

      aPrintStream.push();
      aPrintStream.printlnKeyValue("Version                    ",m_Version);
      aPrintStream.printlnKeyValue("Dispatch Servant PID (HEX) ",m_dispatchServantPID,null);
      aPrintStream.printlnKeyValue("Dispatch Task ID           ",m_dispatchTaskId,null);
      aPrintStream.printlnKeyValue("Dispatch TCB CPU           ",m_dispatchTcbCpu);
      aPrintStream.printlnKeyValue("Completion Minor Code      ",m_completionMinorCode,null);
      aPrintStream.printlnKeyValue("Reserved                   ",m_reserved,null);
      aPrintStream.printlnKeyValueString("Request Type               ",m_requestType,requestTypeString);
      aPrintStream.printlnKeyValue("Reserved                   ",m_theblob,null);
      
      aPrintStream.pop();
      
      
    } // dump()
    
  } // PlatformNeutralRequestInfoSection

