/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2016 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.xholon.service.naturallanguage;

import org.primordion.xholon.base.ISignal;

/**
 * An interface for NaturalLanguage classes.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="">Google natural-language API</a>
 * @see <a href="">Open Calais</a>
 * @since 0.9.1 (Created on December 23, 2016)
 */
public interface INaturalLanguage {
  
  // Google Natural Language API - analyzeEntities
  public static final int SIG_ANALYZE_ENTITIES_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101; // -3898
  public static final int SIG_ANALYZE_ENTITIES_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 201; // -3798
  
  // Google Natural Language API - analyzeSyntax
  public static final int SIG_ANALYZE_SYNTAX_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 102; // -3897
  public static final int SIG_ANALYZE_SYNTAX_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 202; // -3797
  
  // Google Natural Language API - analyzeSentiment
  public static final int SIG_ANALYZE_SENTIMENT_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 103; // -3896
  public static final int SIG_ANALYZE_SENTIMENT_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 203; // -3796
  
  // Import Google Natural Language API tokens into a Xholon tree
  public static final int SIG_IMPORT_TOKENS_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 104; // -3895
  public static final int SIG_IMPORT_TOKENS_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 204; // -3795
  
  // Open Calais (or other) - test
  public static final int SIG_TEST_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 301; // -3698
  public static final int SIG_TEST_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 401; // -3598
  
}
