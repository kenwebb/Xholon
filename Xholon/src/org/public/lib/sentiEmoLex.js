/** NRC Emotion Lexicon
    http://saifmohammad.com/WebPages/AccessResource.htm
    I got the data from tidytext R project
1. NRC Word-Emotion Association Lexicon aka NRC Emotion Lexicon aka EmoLex: association of words with eight emotions (anger, fear, anticipation, trust, surprise, sadness, joy, and disgust) and two sentiments (negative and positive) manually annotated on Amazon's Mechanical Turk. Available in 40 different languages.
Version: 0.92
Number of terms: 14,182 unigrams (words), ~25,000 word senses
Association scores: binary (associated or not)
Creators: Saif M. Mohammad and Peter D. Turney

Papers for 1:

Crowdsourcing a Word-Emotion Association Lexicon, Saif Mohammad and Peter Turney, Computational Intelligence, 29 (3), 436-465, 2013.    Paper (pdf)    BibTeX

Emotions Evoked by Common Words and Phrases: Using Mechanical Turk to Create an Emotion Lexicon, Saif Mohammad and Peter Turney, In Proceedings of the NAACL-HLT 2010 Workshop on Computational Approaches to Analysis and Generation of Emotion in Text, June 2010, LA, California.    Paper (pdf)    BibTeX    Presentation
*/

// This file is used by Ken Webb in the Xholon project.
//   http://www.primordion.com/Xholon/gwt/index.html
//   https://github.com/kenwebb/Xholon
// I selected only 2 columns from the dataset:  word, sentiment
// I converted to Keyed JSON using http://www.convertcsv.com/csv-to-json.htm
// I can then assign the Javascript Object to a Javascript variable
// Then I can query using sen.abandon  OR  sen["abandon"], which returns an array of one or more items or it returns undefined
// ex: sen.abandon[1].sentiment

//xh.sentiments = {
//var sen = {
SentiEmoLex = {
"abacus": [
  {"sentiment": "trust"}
],
"abandon": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"abandoned": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"abandonment": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"abba": [
  {"sentiment": "positive"}
],
"abbot": [
  {"sentiment": "trust"}
],
"abduction": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"aberrant": [
  {"sentiment": "negative"}
],
"aberration": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"abhor": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"abhorrent": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"ability": [
  {"sentiment": "positive"}
],
"abject": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"abnormal": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"abolish": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"abolition": [
  {"sentiment": "negative"}
],
"abominable": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"abomination": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"abort": [
  {"sentiment": "negative"}
],
"abortion": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"abortive": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"abovementioned": [
  {"sentiment": "positive"}
],
"abrasion": [
  {"sentiment": "negative"}
],
"abrogate": [
  {"sentiment": "negative"}
],
"abrupt": [
  {"sentiment": "surprise"}
],
"abscess": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"absence": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"absent": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"absentee": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"absenteeism": [
  {"sentiment": "negative"}
],
"absolute": [
  {"sentiment": "positive"}
],
"absolution": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"absorbed": [
  {"sentiment": "positive"}
],
"absurd": [
  {"sentiment": "negative"}
],
"absurdity": [
  {"sentiment": "negative"}
],
"abundance": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"abundant": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"abuse": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"abysmal": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"abyss": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"academic": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"academy": [
  {"sentiment": "positive"}
],
"accelerate": [
  {"sentiment": "anticipation"}
],
"acceptable": [
  {"sentiment": "positive"}
],
"acceptance": [
  {"sentiment": "positive"}
],
"accessible": [
  {"sentiment": "positive"}
],
"accident": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"accidental": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"accidentally": [
  {"sentiment": "surprise"}
],
"accolade": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"accommodation": [
  {"sentiment": "positive"}
],
"accompaniment": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"accomplish": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"accomplished": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"accomplishment": [
  {"sentiment": "positive"}
],
"accord": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"account": [
  {"sentiment": "trust"}
],
"accountability": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"accountable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"accountant": [
  {"sentiment": "trust"}
],
"accounts": [
  {"sentiment": "trust"}
],
"accredited": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"accueil": [
  {"sentiment": "positive"}
],
"accurate": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"accursed": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"accusation": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"accusative": [
  {"sentiment": "negative"}
],
"accused": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"accuser": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"accusing": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"ace": [
  {"sentiment": "positive"}
],
"ache": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"achieve": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"achievement": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"aching": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"acid": [
  {"sentiment": "negative"}
],
"acknowledgment": [
  {"sentiment": "positive"}
],
"acquire": [
  {"sentiment": "positive"}
],
"acquiring": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"acrobat": [
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"action": [
  {"sentiment": "positive"}
],
"actionable": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"actual": [
  {"sentiment": "positive"}
],
"acuity": [
  {"sentiment": "positive"}
],
"acumen": [
  {"sentiment": "positive"}
],
"adapt": [
  {"sentiment": "positive"}
],
"adaptable": [
  {"sentiment": "positive"}
],
"adder": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"addiction": [
  {"sentiment": "negative"}
],
"addresses": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"adept": [
  {"sentiment": "positive"}
],
"adequacy": [
  {"sentiment": "positive"}
],
"adhering": [
  {"sentiment": "trust"}
],
"adipose": [
  {"sentiment": "negative"}
],
"adjudicate": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"adjunct": [
  {"sentiment": "positive"}
],
"administrative": [
  {"sentiment": "trust"}
],
"admirable": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"admiral": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"admiration": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"admire": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"admirer": [
  {"sentiment": "positive"}
],
"admissible": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"admonition": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"adorable": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"adoration": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"adore": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"adrift": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"adulterated": [
  {"sentiment": "negative"}
],
"adultery": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"advance": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"advanced": [
  {"sentiment": "positive"}
],
"advancement": [
  {"sentiment": "positive"}
],
"advantage": [
  {"sentiment": "positive"}
],
"advantageous": [
  {"sentiment": "positive"}
],
"advent": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"adventure": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"adventurous": [
  {"sentiment": "positive"}
],
"adversary": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"adverse": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"adversity": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"advice": [
  {"sentiment": "trust"}
],
"advisable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"advise": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"advised": [
  {"sentiment": "trust"}
],
"adviser": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"advocacy": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"advocate": [
  {"sentiment": "trust"}
],
"aesthetic": [
  {"sentiment": "positive"}
],
"aesthetics": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"affable": [
  {"sentiment": "positive"}
],
"affection": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"affiliated": [
  {"sentiment": "positive"}
],
"affirm": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"affirmation": [
  {"sentiment": "positive"}
],
"affirmative": [
  {"sentiment": "positive"}
],
"affirmatively": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"afflict": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"afflicted": [
  {"sentiment": "negative"}
],
"affliction": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"affluence": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"affluent": [
  {"sentiment": "positive"}
],
"afford": [
  {"sentiment": "positive"}
],
"affront": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"afraid": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"aftermath": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"aftertaste": [
  {"sentiment": "negative"}
],
"aga": [
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"aggravated": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"aggravating": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"aggravation": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"aggression": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"aggressive": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"aggressor": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"aghast": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"agile": [
  {"sentiment": "positive"}
],
"agility": [
  {"sentiment": "positive"}
],
"agitated": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"agitation": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"agonizing": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"agony": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"agree": [
  {"sentiment": "positive"}
],
"agreeable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"agreed": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"agreeing": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"agreement": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"agriculture": [
  {"sentiment": "positive"}
],
"aground": [
  {"sentiment": "negative"}
],
"ahead": [
  {"sentiment": "positive"}
],
"aid": [
  {"sentiment": "positive"}
],
"aiding": [
  {"sentiment": "positive"}
],
"ail": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ailing": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"aimless": [
  {"sentiment": "negative"}
],
"airport": [
  {"sentiment": "anticipation"}
],
"airs": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"akin": [
  {"sentiment": "trust"}
],
"alabaster": [
  {"sentiment": "positive"}
],
"alarm": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"alarming": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"alb": [
  {"sentiment": "trust"}
],
"alcoholism": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"alertness": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"alerts": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"alien": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"alienate": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"alienated": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"alienation": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"alimentation": [
  {"sentiment": "positive"}
],
"alimony": [
  {"sentiment": "negative"}
],
"alive": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"allay": [
  {"sentiment": "positive"}
],
"allegation": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"allege": [
  {"sentiment": "negative"}
],
"allegiance": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"allegro": [
  {"sentiment": "positive"}
],
"alleviate": [
  {"sentiment": "positive"}
],
"alleviation": [
  {"sentiment": "positive"}
],
"alliance": [
  {"sentiment": "trust"}
],
"allied": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"allowable": [
  {"sentiment": "positive"}
],
"allure": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"alluring": [
  {"sentiment": "positive"}
],
"ally": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"almighty": [
  {"sentiment": "positive"}
],
"aloha": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"aloof": [
  {"sentiment": "negative"}
],
"altercation": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"amaze": [
  {"sentiment": "surprise"}
],
"amazingly": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"ambassador": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"ambiguous": [
  {"sentiment": "negative"}
],
"ambition": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"ambulance": [
  {"sentiment": "fear"},
  {"sentiment": "trust"}
],
"ambush": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"ameliorate": [
  {"sentiment": "positive"}
],
"amen": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"amenable": [
  {"sentiment": "positive"}
],
"amend": [
  {"sentiment": "positive"}
],
"amends": [
  {"sentiment": "positive"}
],
"amenity": [
  {"sentiment": "positive"}
],
"amiable": [
  {"sentiment": "positive"}
],
"amicable": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"ammonia": [
  {"sentiment": "disgust"}
],
"amnesia": [
  {"sentiment": "negative"}
],
"amnesty": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"amortization": [
  {"sentiment": "trust"}
],
"amour": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"amphetamines": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"amuse": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"amused": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"amusement": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"amusing": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"anaconda": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"anal": [
  {"sentiment": "negative"}
],
"analyst": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"anarchism": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"anarchist": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"anarchy": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"anathema": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ancestral": [
  {"sentiment": "trust"}
],
"anchor": [
  {"sentiment": "positive"}
],
"anchorage": [
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"ancient": [
  {"sentiment": "negative"}
],
"angel": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"angelic": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"anger": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"angina": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"angling": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"angry": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"anguish": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"animate": [
  {"sentiment": "positive"}
],
"animated": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"animosity": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"animus": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"annihilate": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"annihilated": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"annihilation": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"announcement": [
  {"sentiment": "anticipation"}
],
"annoy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"annoyance": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"annoying": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"annul": [
  {"sentiment": "negative"}
],
"annulment": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"anomaly": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"anonymous": [
  {"sentiment": "negative"}
],
"answerable": [
  {"sentiment": "trust"}
],
"antagonism": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"antagonist": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"antagonistic": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"anthrax": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"antibiotics": [
  {"sentiment": "positive"}
],
"antichrist": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"anticipation": [
  {"sentiment": "anticipation"}
],
"anticipatory": [
  {"sentiment": "anticipation"}
],
"antidote": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"antifungal": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"antipathy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"antiquated": [
  {"sentiment": "negative"}
],
"antique": [
  {"sentiment": "positive"}
],
"antiseptic": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"antisocial": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"antithesis": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"anxiety": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"anxious": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"apache": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"apathetic": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"apathy": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"aphid": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"aplomb": [
  {"sentiment": "positive"}
],
"apologetic": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"apologize": [
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"apology": [
  {"sentiment": "positive"}
],
"apostle": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"apostolic": [
  {"sentiment": "trust"}
],
"appalling": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"apparition": [
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"appeal": [
  {"sentiment": "anticipation"}
],
"appendicitis": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"applause": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"applicant": [
  {"sentiment": "anticipation"}
],
"appreciation": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"apprehend": [
  {"sentiment": "fear"}
],
"apprehension": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"apprehensive": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"apprentice": [
  {"sentiment": "trust"}
],
"approaching": [
  {"sentiment": "anticipation"}
],
"approbation": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"appropriation": [
  {"sentiment": "negative"}
],
"approval": [
  {"sentiment": "positive"}
],
"approve": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"approving": [
  {"sentiment": "positive"}
],
"apt": [
  {"sentiment": "positive"}
],
"aptitude": [
  {"sentiment": "positive"}
],
"arbiter": [
  {"sentiment": "trust"}
],
"arbitration": [
  {"sentiment": "anticipation"}
],
"arbitrator": [
  {"sentiment": "trust"}
],
"archaeology": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"archaic": [
  {"sentiment": "negative"}
],
"architecture": [
  {"sentiment": "trust"}
],
"ardent": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"ardor": [
  {"sentiment": "positive"}
],
"arduous": [
  {"sentiment": "negative"}
],
"argue": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"argument": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"argumentation": [
  {"sentiment": "anger"}
],
"argumentative": [
  {"sentiment": "negative"}
],
"arguments": [
  {"sentiment": "anger"}
],
"arid": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"aristocracy": [
  {"sentiment": "positive"}
],
"aristocratic": [
  {"sentiment": "positive"}
],
"armament": [
  {"sentiment": "anger"},
  {"sentiment": "fear"}
],
"armaments": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"armed": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"armor": [
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"armored": [
  {"sentiment": "fear"}
],
"armory": [
  {"sentiment": "trust"}
],
"aroma": [
  {"sentiment": "positive"}
],
"arouse": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"arraignment": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"array": [
  {"sentiment": "positive"}
],
"arrears": [
  {"sentiment": "negative"}
],
"arrest": [
  {"sentiment": "negative"}
],
"arrival": [
  {"sentiment": "anticipation"}
],
"arrive": [
  {"sentiment": "anticipation"}
],
"arrogance": [
  {"sentiment": "negative"}
],
"arrogant": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"arsenic": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"arson": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"art": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"articulate": [
  {"sentiment": "positive"}
],
"articulation": [
  {"sentiment": "positive"}
],
"artillery": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"artisan": [
  {"sentiment": "positive"}
],
"artiste": [
  {"sentiment": "positive"}
],
"artistic": [
  {"sentiment": "positive"}
],
"ascendancy": [
  {"sentiment": "positive"}
],
"ascent": [
  {"sentiment": "positive"}
],
"ash": [
  {"sentiment": "negative"}
],
"ashamed": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ashes": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"asp": [
  {"sentiment": "fear"}
],
"aspiration": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"aspire": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"aspiring": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"ass": [
  {"sentiment": "negative"}
],
"assail": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"assailant": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"assassin": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"assassinate": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"assassination": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"assault": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"assembly": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"assent": [
  {"sentiment": "positive"}
],
"asserting": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"assessment": [
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"assessor": [
  {"sentiment": "trust"}
],
"assets": [
  {"sentiment": "positive"}
],
"asshole": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"assignee": [
  {"sentiment": "trust"}
],
"assist": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"assistance": [
  {"sentiment": "positive"}
],
"associate": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"association": [
  {"sentiment": "trust"}
],
"assuage": [
  {"sentiment": "positive"}
],
"assurance": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"assure": [
  {"sentiment": "trust"}
],
"assured": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"assuredly": [
  {"sentiment": "trust"}
],
"astonishingly": [
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"astonishment": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"astray": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"astringent": [
  {"sentiment": "negative"}
],
"astrologer": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"astronaut": [
  {"sentiment": "positive"}
],
"astronomer": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"astute": [
  {"sentiment": "positive"}
],
"asylum": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"asymmetry": [
  {"sentiment": "disgust"}
],
"atheism": [
  {"sentiment": "negative"}
],
"atherosclerosis": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"athlete": [
  {"sentiment": "positive"}
],
"athletic": [
  {"sentiment": "positive"}
],
"atom": [
  {"sentiment": "positive"}
],
"atone": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"atonement": [
  {"sentiment": "positive"}
],
"atrocious": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"atrocity": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"atrophy": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"attachment": [
  {"sentiment": "positive"}
],
"attack": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"attacking": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"attainable": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"attainment": [
  {"sentiment": "positive"}
],
"attempt": [
  {"sentiment": "anticipation"}
],
"attendance": [
  {"sentiment": "anticipation"}
],
"attendant": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"attention": [
  {"sentiment": "positive"}
],
"attentive": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"attenuated": [
  {"sentiment": "negative"}
],
"attenuation": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"attest": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"attestation": [
  {"sentiment": "trust"}
],
"attorney": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"attraction": [
  {"sentiment": "positive"}
],
"attractiveness": [
  {"sentiment": "positive"}
],
"auction": [
  {"sentiment": "anticipation"}
],
"audacity": [
  {"sentiment": "negative"}
],
"audience": [
  {"sentiment": "anticipation"}
],
"auditor": [
  {"sentiment": "fear"},
  {"sentiment": "trust"}
],
"augment": [
  {"sentiment": "positive"}
],
"august": [
  {"sentiment": "positive"}
],
"aunt": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"aura": [
  {"sentiment": "positive"}
],
"auspicious": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"austere": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"austerity": [
  {"sentiment": "negative"}
],
"authentic": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"authenticate": [
  {"sentiment": "trust"}
],
"authentication": [
  {"sentiment": "trust"}
],
"authenticity": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"author": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"authoritative": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"authority": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"authorization": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"authorize": [
  {"sentiment": "trust"}
],
"authorized": [
  {"sentiment": "positive"}
],
"autocratic": [
  {"sentiment": "negative"}
],
"automatic": [
  {"sentiment": "trust"}
],
"autopsy": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"avalanche": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"avarice": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"avatar": [
  {"sentiment": "positive"}
],
"avenger": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"averse": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"aversion": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"avoid": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"avoidance": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"avoiding": [
  {"sentiment": "fear"}
],
"await": [
  {"sentiment": "anticipation"}
],
"award": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"awful": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"awkwardness": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"awry": [
  {"sentiment": "negative"}
],
"axiom": [
  {"sentiment": "trust"}
],
"axiomatic": [
  {"sentiment": "trust"}
],
"ay": [
  {"sentiment": "positive"}
],
"aye": [
  {"sentiment": "positive"}
],
"babble": [
  {"sentiment": "negative"}
],
"babbling": [
  {"sentiment": "negative"}
],
"baboon": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"baby": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"babysitter": [
  {"sentiment": "trust"}
],
"baccalaureate": [
  {"sentiment": "positive"}
],
"backbone": [
  {"sentiment": "anger"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"backer": [
  {"sentiment": "trust"}
],
"backward": [
  {"sentiment": "negative"}
],
"backwards": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"backwater": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bacteria": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bacterium": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bad": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"badge": [
  {"sentiment": "trust"}
],
"badger": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"badly": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"badness": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bailiff": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"bait": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"balance": [
  {"sentiment": "positive"}
],
"balanced": [
  {"sentiment": "positive"}
],
"bale": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"balk": [
  {"sentiment": "negative"}
],
"ballad": [
  {"sentiment": "positive"}
],
"ballet": [
  {"sentiment": "positive"}
],
"ballot": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"balm": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"balsam": [
  {"sentiment": "positive"}
],
"ban": [
  {"sentiment": "negative"}
],
"bandit": [
  {"sentiment": "negative"}
],
"bane": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bang": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"banger": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"banish": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"banished": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"banishment": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bank": [
  {"sentiment": "trust"}
],
"banker": [
  {"sentiment": "trust"}
],
"bankrupt": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bankruptcy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"banquet": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"banshee": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"baptism": [
  {"sentiment": "positive"}
],
"baptismal": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"barb": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"barbarian": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"barbaric": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"barbarism": [
  {"sentiment": "negative"}
],
"bard": [
  {"sentiment": "positive"}
],
"barf": [
  {"sentiment": "disgust"}
],
"bargain": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"bark": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"barred": [
  {"sentiment": "negative"}
],
"barren": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"barricade": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"barrier": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"barrow": [
  {"sentiment": "disgust"}
],
"bartender": [
  {"sentiment": "trust"}
],
"barter": [
  {"sentiment": "trust"}
],
"base": [
  {"sentiment": "trust"}
],
"baseless": [
  {"sentiment": "negative"}
],
"basketball": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"bastard": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bastion": [
  {"sentiment": "anger"},
  {"sentiment": "positive"}
],
"bath": [
  {"sentiment": "positive"}
],
"battalion": [
  {"sentiment": "anger"}
],
"batter": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"battered": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"battery": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"battle": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"battled": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"battlefield": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bawdy": [
  {"sentiment": "negative"}
],
"bayonet": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"beach": [
  {"sentiment": "joy"}
],
"beam": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"beaming": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"bear": [
  {"sentiment": "anger"},
  {"sentiment": "fear"}
],
"bearer": [
  {"sentiment": "negative"}
],
"bearish": [
  {"sentiment": "anger"},
  {"sentiment": "fear"}
],
"beast": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"beastly": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"beating": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"beautification": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"beautiful": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"beautify": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"beauty": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"bedrock": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"bee": [
  {"sentiment": "anger"},
  {"sentiment": "fear"}
],
"beer": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"befall": [
  {"sentiment": "negative"}
],
"befitting": [
  {"sentiment": "positive"}
],
"befriend": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"beg": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"beggar": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"begging": [
  {"sentiment": "negative"}
],
"begun": [
  {"sentiment": "anticipation"}
],
"behemoth": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"beholden": [
  {"sentiment": "negative"}
],
"belated": [
  {"sentiment": "negative"}
],
"believed": [
  {"sentiment": "trust"}
],
"believer": [
  {"sentiment": "trust"}
],
"believing": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"belittle": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"belligerent": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bellows": [
  {"sentiment": "anger"}
],
"belt": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bender": [
  {"sentiment": "negative"}
],
"benefactor": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"beneficial": [
  {"sentiment": "positive"}
],
"benefit": [
  {"sentiment": "positive"}
],
"benevolence": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"benign": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"bequest": [
  {"sentiment": "trust"}
],
"bereaved": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bereavement": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bereft": [
  {"sentiment": "negative"}
],
"berserk": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"berth": [
  {"sentiment": "positive"}
],
"bestial": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"betray": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"betrayal": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"betrothed": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"betterment": [
  {"sentiment": "positive"}
],
"beverage": [
  {"sentiment": "positive"}
],
"beware": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bewildered": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"bewilderment": [
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"bias": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"biased": [
  {"sentiment": "negative"}
],
"biblical": [
  {"sentiment": "positive"}
],
"bickering": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"biennial": [
  {"sentiment": "anticipation"}
],
"bier": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bigot": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bigoted": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bile": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"bilingual": [
  {"sentiment": "positive"}
],
"biopsy": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"birch": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"birth": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"birthday": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"birthplace": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"bitch": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bite": [
  {"sentiment": "negative"}
],
"bitterly": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bitterness": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bizarre": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"black": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"blackjack": [
  {"sentiment": "negative"}
],
"blackmail": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"blackness": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"blame": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"blameless": [
  {"sentiment": "positive"}
],
"bland": [
  {"sentiment": "negative"}
],
"blanket": [
  {"sentiment": "trust"}
],
"blasphemous": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"blasphemy": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"blast": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"blatant": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"blather": [
  {"sentiment": "negative"}
],
"blaze": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"bleak": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bleeding": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"blemish": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bless": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"blessed": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"blessing": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"blessings": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"blight": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"blighted": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"blind": [
  {"sentiment": "negative"}
],
"blinded": [
  {"sentiment": "negative"}
],
"blindfold": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"blindly": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"blindness": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bliss": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"blissful": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"blister": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"blitz": [
  {"sentiment": "surprise"}
],
"bloated": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"blob": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"blockade": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bloodless": [
  {"sentiment": "positive"}
],
"bloodshed": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"bloodthirsty": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bloody": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bloom": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"blossom": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"blot": [
  {"sentiment": "negative"}
],
"blower": [
  {"sentiment": "negative"}
],
"blowout": [
  {"sentiment": "negative"}
],
"blue": [
  {"sentiment": "sadness"}
],
"blues": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bluff": [
  {"sentiment": "negative"}
],
"blunder": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"blur": [
  {"sentiment": "negative"}
],
"blurred": [
  {"sentiment": "negative"}
],
"blush": [
  {"sentiment": "negative"}
],
"board": [
  {"sentiment": "anticipation"}
],
"boast": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"boasting": [
  {"sentiment": "negative"}
],
"bodyguard": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"bog": [
  {"sentiment": "negative"}
],
"bogus": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"boil": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"boilerplate": [
  {"sentiment": "negative"}
],
"boisterous": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"bold": [
  {"sentiment": "positive"}
],
"boldness": [
  {"sentiment": "positive"}
],
"bolster": [
  {"sentiment": "positive"}
],
"bomb": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"bombard": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bombardment": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bombed": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"bomber": [
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"bonanza": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"bondage": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bonds": [
  {"sentiment": "negative"}
],
"bonne": [
  {"sentiment": "positive"}
],
"bonus": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"boo": [
  {"sentiment": "negative"}
],
"booby": [
  {"sentiment": "negative"}
],
"bookish": [
  {"sentiment": "positive"}
],
"bookshop": [
  {"sentiment": "positive"}
],
"bookworm": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"boomerang": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"boon": [
  {"sentiment": "positive"}
],
"booze": [
  {"sentiment": "negative"}
],
"bore": [
  {"sentiment": "negative"}
],
"boredom": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"boring": [
  {"sentiment": "negative"}
],
"borrower": [
  {"sentiment": "negative"}
],
"bother": [
  {"sentiment": "negative"}
],
"bothering": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bottom": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bottomless": [
  {"sentiment": "fear"}
],
"bound": [
  {"sentiment": "negative"}
],
"bountiful": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"bounty": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"bouquet": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"bout": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"bovine": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"bowels": [
  {"sentiment": "disgust"}
],
"boxing": [
  {"sentiment": "anger"}
],
"boy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"boycott": [
  {"sentiment": "negative"}
],
"brag": [
  {"sentiment": "negative"}
],
"brains": [
  {"sentiment": "positive"}
],
"bran": [
  {"sentiment": "disgust"}
],
"brandy": [
  {"sentiment": "negative"}
],
"bravado": [
  {"sentiment": "negative"}
],
"bravery": [
  {"sentiment": "positive"}
],
"brawl": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"brazen": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"breach": [
  {"sentiment": "negative"}
],
"break": [
  {"sentiment": "surprise"}
],
"breakdown": [
  {"sentiment": "negative"}
],
"breakfast": [
  {"sentiment": "positive"}
],
"breakneck": [
  {"sentiment": "negative"}
],
"breakup": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bribe": [
  {"sentiment": "negative"}
],
"bribery": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"bridal": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"bride": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"bridegroom": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"bridesmaid": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"brigade": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"brighten": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"brightness": [
  {"sentiment": "positive"}
],
"brilliant": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"brimstone": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bristle": [
  {"sentiment": "negative"}
],
"broadside": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"brocade": [
  {"sentiment": "positive"}
],
"broil": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"broke": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"broken": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"brothel": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"brother": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"brotherhood": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"brotherly": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"bruise": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"brunt": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"brutal": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"brutality": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"brute": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"buck": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"buddy": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"budget": [
  {"sentiment": "trust"}
],
"buffet": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"bug": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bugaboo": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bugle": [
  {"sentiment": "anticipation"}
],
"build": [
  {"sentiment": "positive"}
],
"building": [
  {"sentiment": "positive"}
],
"bulbous": [
  {"sentiment": "negative"}
],
"bulldog": [
  {"sentiment": "positive"}
],
"bulletproof": [
  {"sentiment": "positive"}
],
"bully": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"bum": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bummer": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"bunker": [
  {"sentiment": "fear"}
],
"buoy": [
  {"sentiment": "positive"}
],
"burdensome": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"bureaucracy": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"bureaucrat": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"burglar": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"burglary": [
  {"sentiment": "negative"}
],
"burial": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"buried": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"burke": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"burlesque": [
  {"sentiment": "surprise"}
],
"burnt": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"bursary": [
  {"sentiment": "trust"}
],
"bury": [
  {"sentiment": "sadness"}
],
"buss": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"busted": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"butcher": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"butler": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"butt": [
  {"sentiment": "negative"}
],
"buttery": [
  {"sentiment": "positive"}
],
"buxom": [
  {"sentiment": "positive"}
],
"buzz": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"buzzed": [
  {"sentiment": "negative"}
],
"bye": [
  {"sentiment": "anticipation"}
],
"bylaw": [
  {"sentiment": "trust"}
],
"cab": [
  {"sentiment": "positive"}
],
"cabal": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"cabinet": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cable": [
  {"sentiment": "surprise"}
],
"cacophony": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"cad": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"cadaver": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"cafe": [
  {"sentiment": "positive"}
],
"cage": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"calamity": [
  {"sentiment": "sadness"}
],
"calculating": [
  {"sentiment": "negative"}
],
"calculation": [
  {"sentiment": "anticipation"}
],
"calculator": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"calf": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"callous": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"calls": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"calm": [
  {"sentiment": "positive"}
],
"camouflage": [
  {"sentiment": "surprise"}
],
"camouflaged": [
  {"sentiment": "surprise"}
],
"campaigning": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"canary": [
  {"sentiment": "positive"}
],
"cancel": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cancer": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"candid": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"candidate": [
  {"sentiment": "positive"}
],
"candied": [
  {"sentiment": "positive"}
],
"cane": [
  {"sentiment": "anger"},
  {"sentiment": "fear"}
],
"canker": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"cannibal": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"cannibalism": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"cannon": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"canons": [
  {"sentiment": "trust"}
],
"cap": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"capitalist": [
  {"sentiment": "positive"}
],
"captain": [
  {"sentiment": "positive"}
],
"captivate": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"captivating": [
  {"sentiment": "positive"}
],
"captive": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"captivity": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"captor": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"capture": [
  {"sentiment": "negative"}
],
"carcass": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"carcinoma": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cardiomyopathy": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"career": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"careful": [
  {"sentiment": "positive"}
],
"carefully": [
  {"sentiment": "positive"}
],
"carelessness": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"caress": [
  {"sentiment": "positive"}
],
"caretaker": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"caricature": [
  {"sentiment": "negative"}
],
"caries": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"carnage": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"carnal": [
  {"sentiment": "negative"}
],
"carnivorous": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"carol": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cartel": [
  {"sentiment": "negative"}
],
"cartridge": [
  {"sentiment": "fear"}
],
"cascade": [
  {"sentiment": "positive"}
],
"case": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cash": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cashier": [
  {"sentiment": "trust"}
],
"casket": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"caste": [
  {"sentiment": "negative"}
],
"casualty": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cataract": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"catastrophe": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"catch": [
  {"sentiment": "surprise"}
],
"catechism": [
  {"sentiment": "disgust"}
],
"categorical": [
  {"sentiment": "positive"}
],
"cater": [
  {"sentiment": "positive"}
],
"cathartic": [
  {"sentiment": "positive"}
],
"cathedral": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"catheter": [
  {"sentiment": "negative"}
],
"caution": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"cautionary": [
  {"sentiment": "fear"}
],
"cautious": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cautiously": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"cede": [
  {"sentiment": "negative"}
],
"celebrated": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"celebrating": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"celebration": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"celebrity": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"celestial": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"cement": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"cemetery": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"censor": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"censure": [
  {"sentiment": "negative"}
],
"center": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"centurion": [
  {"sentiment": "positive"}
],
"cerebral": [
  {"sentiment": "positive"}
],
"ceremony": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"certainty": [
  {"sentiment": "positive"}
],
"certify": [
  {"sentiment": "trust"}
],
"cess": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"cessation": [
  {"sentiment": "negative"}
],
"chaff": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"chafing": [
  {"sentiment": "negative"}
],
"chagrin": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"chairman": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"chairwoman": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"challenge": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"champion": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"chance": [
  {"sentiment": "surprise"}
],
"chancellor": [
  {"sentiment": "trust"}
],
"change": [
  {"sentiment": "fear"}
],
"changeable": [
  {"sentiment": "anticipation"},
  {"sentiment": "surprise"}
],
"chant": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"chaos": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"chaotic": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"chaplain": [
  {"sentiment": "trust"}
],
"charade": [
  {"sentiment": "negative"}
],
"chargeable": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"charger": [
  {"sentiment": "positive"}
],
"charitable": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"charity": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"charm": [
  {"sentiment": "positive"}
],
"charmed": [
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"charming": [
  {"sentiment": "positive"}
],
"chart": [
  {"sentiment": "trust"}
],
"chase": [
  {"sentiment": "negative"}
],
"chasm": [
  {"sentiment": "fear"}
],
"chastisement": [
  {"sentiment": "negative"}
],
"chastity": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"chattering": [
  {"sentiment": "positive"}
],
"chatty": [
  {"sentiment": "negative"}
],
"cheap": [
  {"sentiment": "negative"}
],
"cheat": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"checklist": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cheer": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"cheerful": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"cheerfulness": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cheering": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"cheery": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"cheesecake": [
  {"sentiment": "negative"}
],
"chemist": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cherish": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"cherry": [
  {"sentiment": "positive"}
],
"chicane": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"chicken": [
  {"sentiment": "fear"}
],
"chieftain": [
  {"sentiment": "positive"}
],
"child": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"childhood": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"childish": [
  {"sentiment": "negative"}
],
"chilly": [
  {"sentiment": "negative"}
],
"chimera": [
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"chirp": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"chisel": [
  {"sentiment": "positive"}
],
"chivalry": [
  {"sentiment": "positive"}
],
"chloroform": [
  {"sentiment": "negative"}
],
"chocolate": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"choice": [
  {"sentiment": "positive"}
],
"choir": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"choke": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cholera": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"chop": [
  {"sentiment": "negative"}
],
"choral": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"chore": [
  {"sentiment": "negative"}
],
"chorus": [
  {"sentiment": "positive"}
],
"chosen": [
  {"sentiment": "positive"}
],
"chowder": [
  {"sentiment": "positive"}
],
"chronic": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"chronicle": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"chuckle": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"church": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cider": [
  {"sentiment": "positive"}
],
"cigarette": [
  {"sentiment": "negative"}
],
"circumcision": [
  {"sentiment": "positive"}
],
"circumvention": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"citizen": [
  {"sentiment": "positive"}
],
"civil": [
  {"sentiment": "positive"}
],
"civility": [
  {"sentiment": "positive"}
],
"civilization": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"civilized": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"claimant": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"}
],
"clairvoyant": [
  {"sentiment": "positive"}
],
"clamor": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"clan": [
  {"sentiment": "trust"}
],
"clap": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"clarify": [
  {"sentiment": "positive"}
],
"clash": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"clashing": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"classic": [
  {"sentiment": "positive"}
],
"classical": [
  {"sentiment": "positive"}
],
"classics": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"classify": [
  {"sentiment": "positive"}
],
"claw": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"clean": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cleaning": [
  {"sentiment": "positive"}
],
"cleanliness": [
  {"sentiment": "positive"}
],
"cleanly": [
  {"sentiment": "positive"}
],
"cleanse": [
  {"sentiment": "positive"}
],
"cleansing": [
  {"sentiment": "positive"}
],
"clearance": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"clearness": [
  {"sentiment": "positive"}
],
"cleave": [
  {"sentiment": "fear"}
],
"clerical": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"clever": [
  {"sentiment": "positive"}
],
"cleverness": [
  {"sentiment": "positive"}
],
"cliff": [
  {"sentiment": "fear"}
],
"climax": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"clock": [
  {"sentiment": "anticipation"}
],
"cloister": [
  {"sentiment": "negative"}
],
"closeness": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"closure": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"clothe": [
  {"sentiment": "positive"}
],
"clouded": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cloudiness": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"cloudy": [
  {"sentiment": "sadness"}
],
"clown": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"clue": [
  {"sentiment": "anticipation"}
],
"clump": [
  {"sentiment": "negative"}
],
"clumsy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"coach": [
  {"sentiment": "trust"}
],
"coalesce": [
  {"sentiment": "trust"}
],
"coalition": [
  {"sentiment": "positive"}
],
"coast": [
  {"sentiment": "positive"}
],
"coax": [
  {"sentiment": "trust"}
],
"cobra": [
  {"sentiment": "fear"}
],
"cocaine": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"coerce": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"coercion": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"coexist": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"coexisting": [
  {"sentiment": "trust"}
],
"coffin": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cogent": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cognitive": [
  {"sentiment": "positive"}
],
"coherence": [
  {"sentiment": "positive"}
],
"coherent": [
  {"sentiment": "positive"}
],
"cohesion": [
  {"sentiment": "trust"}
],
"cohesive": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"coincidence": [
  {"sentiment": "surprise"}
],
"cold": [
  {"sentiment": "negative"}
],
"coldly": [
  {"sentiment": "negative"}
],
"coldness": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"colic": [
  {"sentiment": "negative"}
],
"collaborator": [
  {"sentiment": "trust"}
],
"collapse": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"collateral": [
  {"sentiment": "trust"}
],
"collectively": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"collision": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"collusion": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"colonel": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"colossal": [
  {"sentiment": "positive"}
],
"coma": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"comatose": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"combat": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"combatant": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"combative": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"comfort": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"coming": [
  {"sentiment": "anticipation"}
],
"commandant": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"commanding": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"commemorate": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"commemoration": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"commemorative": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"commend": [
  {"sentiment": "positive"}
],
"commendable": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"commentator": [
  {"sentiment": "positive"}
],
"commerce": [
  {"sentiment": "trust"}
],
"commission": [
  {"sentiment": "trust"}
],
"committal": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"committed": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"committee": [
  {"sentiment": "trust"}
],
"commodore": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"commonplace": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"commonwealth": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"commotion": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"communicate": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"communication": [
  {"sentiment": "trust"}
],
"communicative": [
  {"sentiment": "positive"}
],
"communion": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"communism": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"communist": [
  {"sentiment": "negative"}
],
"community": [
  {"sentiment": "positive"}
],
"commutation": [
  {"sentiment": "positive"}
],
"commute": [
  {"sentiment": "positive"}
],
"compact": [
  {"sentiment": "trust"}
],
"companion": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"compass": [
  {"sentiment": "trust"}
],
"compassion": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"compassionate": [
  {"sentiment": "positive"}
],
"compatibility": [
  {"sentiment": "positive"}
],
"compatible": [
  {"sentiment": "positive"}
],
"compelling": [
  {"sentiment": "positive"}
],
"compensate": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"compensatory": [
  {"sentiment": "positive"}
],
"competence": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"competency": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"competent": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"competition": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"complacency": [
  {"sentiment": "positive"}
],
"complain": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"complaint": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"complement": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"complementary": [
  {"sentiment": "positive"}
],
"completely": [
  {"sentiment": "positive"}
],
"completeness": [
  {"sentiment": "positive"}
],
"completing": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"completion": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"complexed": [
  {"sentiment": "negative"}
],
"complexity": [
  {"sentiment": "negative"}
],
"compliance": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"compliant": [
  {"sentiment": "positive"}
],
"complicate": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"complicated": [
  {"sentiment": "negative"}
],
"complication": [
  {"sentiment": "negative"}
],
"complicity": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"compliment": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"composed": [
  {"sentiment": "positive"}
],
"composer": [
  {"sentiment": "positive"}
],
"compost": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"composure": [
  {"sentiment": "positive"}
],
"comprehend": [
  {"sentiment": "positive"}
],
"comprehensive": [
  {"sentiment": "positive"}
],
"compress": [
  {"sentiment": "anger"}
],
"comptroller": [
  {"sentiment": "trust"}
],
"compulsion": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"compulsory": [
  {"sentiment": "negative"}
],
"comrade": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"conceal": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"concealed": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"concealment": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"conceit": [
  {"sentiment": "negative"}
],
"conceited": [
  {"sentiment": "negative"}
],
"concentric": [
  {"sentiment": "positive"}
],
"concerned": [
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"conciliation": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"concluding": [
  {"sentiment": "positive"}
],
"concord": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"concordance": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"concussion": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"condemn": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"condemnation": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"condescending": [
  {"sentiment": "negative"}
],
"condescension": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"condolence": [
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"condone": [
  {"sentiment": "positive"}
],
"conducive": [
  {"sentiment": "positive"}
],
"conductivity": [
  {"sentiment": "positive"}
],
"confederate": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"confess": [
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"confession": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"confessional": [
  {"sentiment": "fear"},
  {"sentiment": "trust"}
],
"confide": [
  {"sentiment": "trust"}
],
"confidence": [
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"confident": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"confidential": [
  {"sentiment": "trust"}
],
"confidentially": [
  {"sentiment": "trust"}
],
"confine": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"confined": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"confinement": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"confirmation": [
  {"sentiment": "trust"}
],
"confirmed": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"confiscate": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"confiscation": [
  {"sentiment": "negative"}
],
"conflagration": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"conflict": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"conflicting": [
  {"sentiment": "negative"}
],
"conformance": [
  {"sentiment": "positive"}
],
"conformity": [
  {"sentiment": "trust"}
],
"confound": [
  {"sentiment": "negative"}
],
"confounded": [
  {"sentiment": "negative"}
],
"confront": [
  {"sentiment": "anger"}
],
"confuse": [
  {"sentiment": "negative"}
],
"confusion": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"congenial": [
  {"sentiment": "positive"}
],
"congestion": [
  {"sentiment": "negative"}
],
"conglomerate": [
  {"sentiment": "trust"}
],
"congratulatory": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"congregation": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"congress": [
  {"sentiment": "disgust"},
  {"sentiment": "trust"}
],
"congressman": [
  {"sentiment": "trust"}
],
"congruence": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"conjecture": [
  {"sentiment": "anticipation"}
],
"conjure": [
  {"sentiment": "anticipation"},
  {"sentiment": "surprise"}
],
"conjuring": [
  {"sentiment": "negative"}
],
"connective": [
  {"sentiment": "trust"}
],
"connoisseur": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"conquest": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"conscience": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"conscientious": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"consciousness": [
  {"sentiment": "positive"}
],
"consecration": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"consequent": [
  {"sentiment": "anticipation"}
],
"conservation": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"conserve": [
  {"sentiment": "positive"}
],
"considerable": [
  {"sentiment": "positive"}
],
"considerate": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"consistency": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"console": [
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"consonant": [
  {"sentiment": "positive"}
],
"consort": [
  {"sentiment": "trust"}
],
"conspiracy": [
  {"sentiment": "fear"}
],
"conspirator": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"conspire": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"constable": [
  {"sentiment": "trust"}
],
"constancy": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"constant": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"constantly": [
  {"sentiment": "trust"}
],
"consternation": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"constipation": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"constitute": [
  {"sentiment": "trust"}
],
"constitutional": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"constrain": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"constrained": [
  {"sentiment": "negative"}
],
"constraint": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"construct": [
  {"sentiment": "positive"}
],
"consul": [
  {"sentiment": "trust"}
],
"consult": [
  {"sentiment": "trust"}
],
"consummate": [
  {"sentiment": "positive"}
],
"contact": [
  {"sentiment": "positive"}
],
"contagion": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"contagious": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"contaminate": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"contaminated": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"contamination": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"contemplation": [
  {"sentiment": "positive"}
],
"contempt": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"contemptible": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"contemptuous": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"content": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"contentious": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"contingent": [
  {"sentiment": "anticipation"}
],
"continuation": [
  {"sentiment": "anticipation"}
],
"continue": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"contour": [
  {"sentiment": "positive"}
],
"contraband": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"contracted": [
  {"sentiment": "negative"}
],
"contradict": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"contradiction": [
  {"sentiment": "negative"}
],
"contradictory": [
  {"sentiment": "negative"}
],
"contrary": [
  {"sentiment": "negative"}
],
"contrasted": [
  {"sentiment": "negative"}
],
"contravene": [
  {"sentiment": "negative"}
],
"contravention": [
  {"sentiment": "negative"}
],
"contribute": [
  {"sentiment": "positive"}
],
"contributor": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"controversial": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"controversy": [
  {"sentiment": "negative"}
],
"convenience": [
  {"sentiment": "positive"}
],
"convenient": [
  {"sentiment": "positive"}
],
"convent": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"convention": [
  {"sentiment": "positive"}
],
"convergence": [
  {"sentiment": "anticipation"}
],
"conversant": [
  {"sentiment": "positive"}
],
"conversational": [
  {"sentiment": "positive"}
],
"convert": [
  {"sentiment": "positive"}
],
"conveyancing": [
  {"sentiment": "trust"}
],
"convict": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"conviction": [
  {"sentiment": "negative"}
],
"convince": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"convinced": [
  {"sentiment": "trust"}
],
"convincing": [
  {"sentiment": "trust"}
],
"cool": [
  {"sentiment": "positive"}
],
"coolness": [
  {"sentiment": "positive"}
],
"coop": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"cooperate": [
  {"sentiment": "positive"}
],
"cooperating": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cooperation": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cooperative": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cop": [
  {"sentiment": "fear"},
  {"sentiment": "trust"}
],
"copy": [
  {"sentiment": "negative"}
],
"copycat": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"core": [
  {"sentiment": "positive"}
],
"coronation": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"coroner": [
  {"sentiment": "negative"}
],
"corporal": [
  {"sentiment": "negative"}
],
"corporation": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"corporeal": [
  {"sentiment": "positive"}
],
"corpse": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"correction": [
  {"sentiment": "negative"}
],
"corrective": [
  {"sentiment": "positive"}
],
"correctness": [
  {"sentiment": "trust"}
],
"correspondence": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"corroborate": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"corroboration": [
  {"sentiment": "trust"}
],
"corrosion": [
  {"sentiment": "negative"}
],
"corrosive": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"corrupt": [
  {"sentiment": "negative"}
],
"corrupting": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"corruption": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"corse": [
  {"sentiment": "sadness"}
],
"cosmopolitan": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cosy": [
  {"sentiment": "positive"}
],
"couch": [
  {"sentiment": "sadness"}
],
"cough": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"council": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"counsel": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"counsellor": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"counselor": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"count": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"countdown": [
  {"sentiment": "anticipation"}
],
"countess": [
  {"sentiment": "positive"}
],
"countryman": [
  {"sentiment": "trust"}
],
"county": [
  {"sentiment": "trust"}
],
"coup": [
  {"sentiment": "anger"},
  {"sentiment": "surprise"}
],
"courage": [
  {"sentiment": "positive"}
],
"courageous": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"courier": [
  {"sentiment": "trust"}
],
"coursing": [
  {"sentiment": "negative"}
],
"court": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"}
],
"courteous": [
  {"sentiment": "positive"}
],
"courtesy": [
  {"sentiment": "positive"}
],
"courtship": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cove": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"covenant": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cover": [
  {"sentiment": "trust"}
],
"covet": [
  {"sentiment": "negative"}
],
"coward": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cowardice": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"cowardly": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"coy": [
  {"sentiment": "fear"}
],
"coyote": [
  {"sentiment": "fear"}
],
"crabby": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"crack": [
  {"sentiment": "negative"}
],
"cracked": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"cracking": [
  {"sentiment": "negative"}
],
"cradle": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"craft": [
  {"sentiment": "positive"}
],
"craftsman": [
  {"sentiment": "positive"}
],
"cramp": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"cramped": [
  {"sentiment": "negative"}
],
"crank": [
  {"sentiment": "negative"}
],
"cranky": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"crap": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"craps": [
  {"sentiment": "anticipation"}
],
"crash": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"crave": [
  {"sentiment": "anticipation"}
],
"craving": [
  {"sentiment": "anticipation"}
],
"crawl": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"crazed": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"crazy": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"creaking": [
  {"sentiment": "negative"}
],
"cream": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"create": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"creative": [
  {"sentiment": "positive"}
],
"creature": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"credence": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"credential": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"credibility": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"credible": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"credit": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"creditable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"credited": [
  {"sentiment": "positive"}
],
"creep": [
  {"sentiment": "negative"}
],
"creeping": [
  {"sentiment": "anticipation"}
],
"cremation": [
  {"sentiment": "sadness"}
],
"crescendo": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"crew": [
  {"sentiment": "trust"}
],
"crime": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"criminal": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"criminality": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"cringe": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cripple": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"crippled": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"crisis": [
  {"sentiment": "negative"}
],
"crisp": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"critic": [
  {"sentiment": "negative"}
],
"criticism": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"criticize": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"critique": [
  {"sentiment": "positive"}
],
"critter": [
  {"sentiment": "disgust"}
],
"crocodile": [
  {"sentiment": "fear"}
],
"crook": [
  {"sentiment": "negative"}
],
"cross": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"crouch": [
  {"sentiment": "fear"}
],
"crouching": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"crowning": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"crucial": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cruciate": [
  {"sentiment": "negative"}
],
"crucifixion": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"crude": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"cruel": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cruelly": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"cruelty": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"crumbling": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"crunch": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"crusade": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"crushed": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"crushing": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"crusty": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"cry": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"crying": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"crypt": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"crystal": [
  {"sentiment": "positive"}
],
"cube": [
  {"sentiment": "trust"}
],
"cuckold": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"cuckoo": [
  {"sentiment": "negative"}
],
"cuddle": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cue": [
  {"sentiment": "anticipation"}
],
"culinary": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cull": [
  {"sentiment": "negative"}
],
"culmination": [
  {"sentiment": "positive"}
],
"culpability": [
  {"sentiment": "negative"}
],
"culpable": [
  {"sentiment": "negative"}
],
"culprit": [
  {"sentiment": "negative"}
],
"cult": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"cultivate": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"cultivated": [
  {"sentiment": "positive"}
],
"cultivation": [
  {"sentiment": "positive"}
],
"culture": [
  {"sentiment": "positive"}
],
"cumbersome": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cunning": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"cupping": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cur": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"curable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"curiosity": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"curl": [
  {"sentiment": "positive"}
],
"curse": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cursed": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cursing": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"cursory": [
  {"sentiment": "negative"}
],
"cushion": [
  {"sentiment": "positive"}
],
"cussed": [
  {"sentiment": "anger"}
],
"custodian": [
  {"sentiment": "trust"}
],
"custody": [
  {"sentiment": "trust"}
],
"customer": [
  {"sentiment": "positive"}
],
"cute": [
  {"sentiment": "positive"}
],
"cutter": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"cutters": [
  {"sentiment": "positive"}
],
"cutthroat": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"cutting": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cyanide": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"cyclone": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"cyst": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"cystic": [
  {"sentiment": "disgust"}
],
"cytomegalovirus": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dabbling": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"daemon": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"daft": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"dagger": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"daily": [
  {"sentiment": "anticipation"}
],
"damage": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"damages": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dame": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"damn": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"damnation": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"damned": [
  {"sentiment": "negative"}
],
"damper": [
  {"sentiment": "negative"}
],
"dance": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"dandruff": [
  {"sentiment": "negative"}
],
"dandy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"danger": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dangerous": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"dank": [
  {"sentiment": "disgust"}
],
"dare": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"daring": [
  {"sentiment": "positive"}
],
"dark": [
  {"sentiment": "sadness"}
],
"darken": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"darkened": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"darkness": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"darling": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"dart": [
  {"sentiment": "fear"}
],
"dashed": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dashing": [
  {"sentiment": "positive"}
],
"dastardly": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"daughter": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"dawn": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"dazed": [
  {"sentiment": "negative"}
],
"deacon": [
  {"sentiment": "trust"}
],
"deactivate": [
  {"sentiment": "negative"}
],
"deadlock": [
  {"sentiment": "negative"}
],
"deadly": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deaf": [
  {"sentiment": "negative"}
],
"deal": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"dealings": [
  {"sentiment": "trust"}
],
"dear": [
  {"sentiment": "positive"}
],
"death": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"debacle": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"debate": [
  {"sentiment": "positive"}
],
"debauchery": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"debenture": [
  {"sentiment": "anticipation"}
],
"debris": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"debt": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"debtor": [
  {"sentiment": "negative"}
],
"decay": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"decayed": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deceased": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deceit": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"deceitful": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deceive": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deceived": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"deceiving": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"decency": [
  {"sentiment": "positive"}
],
"decent": [
  {"sentiment": "positive"}
],
"deception": [
  {"sentiment": "negative"}
],
"deceptive": [
  {"sentiment": "negative"}
],
"declaratory": [
  {"sentiment": "positive"}
],
"declination": [
  {"sentiment": "negative"}
],
"decline": [
  {"sentiment": "negative"}
],
"declining": [
  {"sentiment": "negative"}
],
"decompose": [
  {"sentiment": "disgust"}
],
"decomposed": [
  {"sentiment": "sadness"}
],
"decomposition": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"decoy": [
  {"sentiment": "surprise"}
],
"decrease": [
  {"sentiment": "negative"}
],
"decrement": [
  {"sentiment": "negative"}
],
"decrepit": [
  {"sentiment": "negative"}
],
"decry": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"dedication": [
  {"sentiment": "positive"}
],
"deduct": [
  {"sentiment": "negative"}
],
"deed": [
  {"sentiment": "trust"}
],
"defamation": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"defamatory": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"default": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"defeat": [
  {"sentiment": "negative"}
],
"defeated": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"defect": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"defection": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"defective": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"defend": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"defendant": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"defended": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"defender": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"defending": [
  {"sentiment": "positive"}
],
"defense": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"defenseless": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deference": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"deferral": [
  {"sentiment": "negative"}
],
"defiance": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"defiant": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"deficiency": [
  {"sentiment": "negative"}
],
"deficit": [
  {"sentiment": "negative"}
],
"definitive": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"deflate": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deflation": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"deform": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"deformed": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deformity": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"defraud": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"defunct": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"defy": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"degeneracy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"degenerate": [
  {"sentiment": "negative"}
],
"degradation": [
  {"sentiment": "negative"}
],
"degrade": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"degrading": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"degree": [
  {"sentiment": "positive"}
],
"delay": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"delayed": [
  {"sentiment": "negative"}
],
"delectable": [
  {"sentiment": "positive"}
],
"delegate": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"deleterious": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"deletion": [
  {"sentiment": "negative"}
],
"deliberate": [
  {"sentiment": "positive"}
],
"delicious": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"delight": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"delighted": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"delightful": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"delinquency": [
  {"sentiment": "negative"}
],
"delinquent": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"delirious": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"delirium": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deliverance": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"delivery": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"deluge": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"delusion": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"delusional": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"demand": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"demanding": [
  {"sentiment": "negative"}
],
"demented": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"dementia": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"demise": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"democracy": [
  {"sentiment": "positive"}
],
"demolish": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"demolition": [
  {"sentiment": "negative"}
],
"demon": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"demonic": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"demonstrable": [
  {"sentiment": "positive"}
],
"demonstrative": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"demoralized": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"denial": [
  {"sentiment": "negative"}
],
"denied": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"denounce": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"dentistry": [
  {"sentiment": "fear"}
],
"denunciation": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"deny": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"denying": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"depart": [
  {"sentiment": "anticipation"},
  {"sentiment": "sadness"}
],
"departed": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"departure": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"depend": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"dependence": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dependency": [
  {"sentiment": "negative"}
],
"dependent": [
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"deplorable": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deplore": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deport": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deportation": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"depository": [
  {"sentiment": "trust"}
],
"depraved": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"depravity": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"depreciate": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"depreciated": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"depreciation": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"depress": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"depressed": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"depressing": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"depression": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"depressive": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deprivation": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"depth": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"deputy": [
  {"sentiment": "trust"}
],
"deranged": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"derelict": [
  {"sentiment": "negative"}
],
"derision": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"dermatologist": [
  {"sentiment": "trust"}
],
"derogation": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"derogatory": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"descent": [
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"descriptive": [
  {"sentiment": "positive"}
],
"desecration": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"desert": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deserted": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"desertion": [
  {"sentiment": "negative"}
],
"deserve": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"deserved": [
  {"sentiment": "positive"}
],
"designation": [
  {"sentiment": "trust"}
],
"designer": [
  {"sentiment": "positive"}
],
"desirable": [
  {"sentiment": "positive"}
],
"desiring": [
  {"sentiment": "positive"}
],
"desirous": [
  {"sentiment": "positive"}
],
"desist": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"desolation": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"despair": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"despairing": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"desperate": [
  {"sentiment": "negative"}
],
"despicable": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"despise": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"despotic": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"despotism": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"destination": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"destined": [
  {"sentiment": "anticipation"}
],
"destitute": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"destroyed": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"destroyer": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"destroying": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"destruction": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"destructive": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"detachment": [
  {"sentiment": "negative"}
],
"detain": [
  {"sentiment": "negative"}
],
"detainee": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"detect": [
  {"sentiment": "positive"}
],
"detection": [
  {"sentiment": "positive"}
],
"detention": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deteriorate": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deteriorated": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"deterioration": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"determinate": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"determination": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"determined": [
  {"sentiment": "positive"}
],
"detest": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"detonate": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"detonation": [
  {"sentiment": "anger"}
],
"detract": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"detriment": [
  {"sentiment": "negative"}
],
"detrimental": [
  {"sentiment": "negative"}
],
"detritus": [
  {"sentiment": "negative"}
],
"devastate": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"devastating": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"devastation": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"develop": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"deviation": [
  {"sentiment": "sadness"}
],
"devil": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"devilish": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"devious": [
  {"sentiment": "negative"}
],
"devolution": [
  {"sentiment": "negative"}
],
"devotional": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"devour": [
  {"sentiment": "negative"}
],
"devout": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"dexterity": [
  {"sentiment": "positive"}
],
"diabolical": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"diagnosis": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"diamond": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"diaper": [
  {"sentiment": "disgust"}
],
"diarrhoea": [
  {"sentiment": "disgust"}
],
"diary": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"diatribe": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"dictator": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"dictatorial": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"dictatorship": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dictionary": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"dictum": [
  {"sentiment": "trust"}
],
"didactic": [
  {"sentiment": "positive"}
],
"die": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dietary": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"differential": [
  {"sentiment": "trust"}
],
"differently": [
  {"sentiment": "surprise"}
],
"difficult": [
  {"sentiment": "fear"}
],
"difficulties": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"difficulty": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"digit": [
  {"sentiment": "trust"}
],
"dignified": [
  {"sentiment": "positive"}
],
"dignity": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"digress": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"dike": [
  {"sentiment": "fear"}
],
"dilapidated": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"diligence": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"dilute": [
  {"sentiment": "negative"}
],
"diminish": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"diminished": [
  {"sentiment": "negative"}
],
"din": [
  {"sentiment": "negative"}
],
"dinner": [
  {"sentiment": "positive"}
],
"dinosaur": [
  {"sentiment": "fear"}
],
"diplomacy": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"diplomatic": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"dire": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"director": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"dirt": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"dirty": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"disability": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disable": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disabled": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disaffected": [
  {"sentiment": "negative"}
],
"disagree": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"disagreeing": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disagreement": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disallowed": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disappear": [
  {"sentiment": "fear"}
],
"disappoint": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disappointed": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disappointing": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disappointment": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disapproval": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disapprove": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disapproved": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disapproving": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disaster": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"disastrous": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disbelieve": [
  {"sentiment": "negative"}
],
"discards": [
  {"sentiment": "negative"}
],
"discharge": [
  {"sentiment": "negative"}
],
"disciple": [
  {"sentiment": "trust"}
],
"discipline": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"disclaim": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"disclosed": [
  {"sentiment": "trust"}
],
"discoloration": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"discolored": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"discomfort": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disconnect": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disconnected": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disconnection": [
  {"sentiment": "negative"}
],
"discontent": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"discontinue": [
  {"sentiment": "negative"}
],
"discontinuity": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"discord": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"discourage": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"discouragement": [
  {"sentiment": "negative"}
],
"discovery": [
  {"sentiment": "positive"}
],
"discredit": [
  {"sentiment": "negative"}
],
"discreet": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"discretion": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"discretionary": [
  {"sentiment": "positive"}
],
"discriminate": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"discriminating": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"discrimination": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"discussion": [
  {"sentiment": "positive"}
],
"disdain": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"disease": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"diseased": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disembodied": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disengagement": [
  {"sentiment": "negative"}
],
"disfigured": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disgrace": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disgraced": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disgraceful": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"disgruntled": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disgust": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disgusting": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"disheartened": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disheartening": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dishonest": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dishonesty": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"dishonor": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disillusionment": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disinfection": [
  {"sentiment": "positive"}
],
"disinformation": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"disingenuous": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"disintegrate": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"disintegration": [
  {"sentiment": "negative"}
],
"disinterested": [
  {"sentiment": "negative"}
],
"dislike": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"disliked": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dislocated": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dismal": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dismay": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"dismemberment": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dismissal": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"disobedience": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"disobedient": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"disobey": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"disorder": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"disorderly": [
  {"sentiment": "negative"}
],
"disorganized": [
  {"sentiment": "negative"}
],
"disparage": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disparaging": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disparity": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dispassionate": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dispel": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dispersion": [
  {"sentiment": "negative"}
],
"displace": [
  {"sentiment": "negative"}
],
"displaced": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"displeased": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"displeasure": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"disposal": [
  {"sentiment": "negative"}
],
"dispose": [
  {"sentiment": "disgust"}
],
"disposed": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"dispossessed": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dispute": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"disqualification": [
  {"sentiment": "negative"}
],
"disqualified": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disqualify": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disregard": [
  {"sentiment": "negative"}
],
"disregarded": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"disreputable": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"disrespect": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"disrespectful": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disruption": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"dissatisfaction": [
  {"sentiment": "negative"}
],
"dissection": [
  {"sentiment": "disgust"}
],
"disseminate": [
  {"sentiment": "positive"}
],
"dissension": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"dissenting": [
  {"sentiment": "negative"}
],
"disservice": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dissident": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"dissolution": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"dissonance": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"distaste": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"distasteful": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"distillation": [
  {"sentiment": "positive"}
],
"distinction": [
  {"sentiment": "positive"}
],
"distorted": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"distortion": [
  {"sentiment": "negative"}
],
"distract": [
  {"sentiment": "negative"}
],
"distracted": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"distracting": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"distraction": [
  {"sentiment": "negative"}
],
"distraught": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"distress": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"distressed": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"distressing": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"distrust": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"disturbance": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"disturbed": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"disuse": [
  {"sentiment": "negative"}
],
"disused": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"ditty": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"divan": [
  {"sentiment": "trust"}
],
"divergent": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"diverse": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"diversified": [
  {"sentiment": "positive"}
],
"diversion": [
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"divested": [
  {"sentiment": "negative"}
],
"divestment": [
  {"sentiment": "negative"}
],
"divination": [
  {"sentiment": "anticipation"}
],
"divinity": [
  {"sentiment": "positive"}
],
"divorce": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"dizziness": [
  {"sentiment": "negative"}
],
"dizzy": [
  {"sentiment": "negative"}
],
"docked": [
  {"sentiment": "negative"}
],
"doctor": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"doctrine": [
  {"sentiment": "trust"}
],
"doer": [
  {"sentiment": "positive"}
],
"dogged": [
  {"sentiment": "positive"}
],
"dogma": [
  {"sentiment": "trust"}
],
"doit": [
  {"sentiment": "negative"}
],
"doldrums": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dole": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"doll": [
  {"sentiment": "joy"}
],
"dolor": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dolphin": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"dominant": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"dominate": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"domination": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dominion": [
  {"sentiment": "fear"},
  {"sentiment": "trust"}
],
"don": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"donation": [
  {"sentiment": "positive"}
],
"donkey": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"doodle": [
  {"sentiment": "negative"}
],
"doom": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"doomed": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"doomsday": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"doubt": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"doubtful": [
  {"sentiment": "negative"}
],
"doubting": [
  {"sentiment": "negative"}
],
"doubtless": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"douche": [
  {"sentiment": "negative"}
],
"dour": [
  {"sentiment": "negative"}
],
"dove": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"downfall": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"downright": [
  {"sentiment": "trust"}
],
"downy": [
  {"sentiment": "positive"}
],
"drab": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"draft": [
  {"sentiment": "anticipation"}
],
"dragon": [
  {"sentiment": "fear"}
],
"drainage": [
  {"sentiment": "negative"}
],
"drawback": [
  {"sentiment": "negative"}
],
"dread": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"dreadful": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dreadfully": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"dreary": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"drinking": [
  {"sentiment": "negative"}
],
"drivel": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"drone": [
  {"sentiment": "negative"}
],
"drool": [
  {"sentiment": "disgust"}
],
"drooping": [
  {"sentiment": "negative"}
],
"drought": [
  {"sentiment": "negative"}
],
"drown": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"drowsiness": [
  {"sentiment": "negative"}
],
"drudgery": [
  {"sentiment": "negative"}
],
"drugged": [
  {"sentiment": "sadness"}
],
"drunken": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"drunkenness": [
  {"sentiment": "negative"}
],
"dubious": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"duel": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"}
],
"duet": [
  {"sentiment": "positive"}
],
"duke": [
  {"sentiment": "positive"}
],
"dull": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dumb": [
  {"sentiment": "negative"}
],
"dummy": [
  {"sentiment": "negative"}
],
"dumps": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dun": [
  {"sentiment": "negative"}
],
"dung": [
  {"sentiment": "disgust"}
],
"dungeon": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"dupe": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"duplicity": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"durability": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"durable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"duress": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dust": [
  {"sentiment": "negative"}
],
"dutiful": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"dwarfed": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dying": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"dynamic": [
  {"sentiment": "surprise"}
],
"dysentery": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"eager": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"eagerness": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"eagle": [
  {"sentiment": "trust"}
],
"earl": [
  {"sentiment": "positive"}
],
"earn": [
  {"sentiment": "positive"}
],
"earnest": [
  {"sentiment": "positive"}
],
"earnestly": [
  {"sentiment": "positive"}
],
"earnestness": [
  {"sentiment": "positive"}
],
"earthquake": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"ease": [
  {"sentiment": "positive"}
],
"easement": [
  {"sentiment": "positive"}
],
"easygoing": [
  {"sentiment": "positive"}
],
"eat": [
  {"sentiment": "positive"}
],
"eavesdropping": [
  {"sentiment": "negative"}
],
"economy": [
  {"sentiment": "trust"}
],
"ecstasy": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"ecstatic": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"edict": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"edification": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"edition": [
  {"sentiment": "anticipation"}
],
"educate": [
  {"sentiment": "positive"}
],
"educated": [
  {"sentiment": "positive"}
],
"educational": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"eel": [
  {"sentiment": "fear"}
],
"effective": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"effeminate": [
  {"sentiment": "negative"}
],
"efficacy": [
  {"sentiment": "positive"}
],
"efficiency": [
  {"sentiment": "positive"}
],
"efficient": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"effigy": [
  {"sentiment": "anger"}
],
"effort": [
  {"sentiment": "positive"}
],
"egotistical": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"egregious": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"ejaculation": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"eject": [
  {"sentiment": "negative"}
],
"ejection": [
  {"sentiment": "negative"}
],
"elaboration": [
  {"sentiment": "positive"}
],
"elated": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"elbow": [
  {"sentiment": "anger"}
],
"elder": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"elders": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"elect": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"electorate": [
  {"sentiment": "trust"}
],
"electric": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"electricity": [
  {"sentiment": "positive"}
],
"elegance": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"elegant": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"elevation": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"elf": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"}
],
"eligible": [
  {"sentiment": "positive"}
],
"elimination": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"elite": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"eloquence": [
  {"sentiment": "positive"}
],
"eloquent": [
  {"sentiment": "positive"}
],
"elucidate": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"elusive": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"emaciated": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"emancipation": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"embargo": [
  {"sentiment": "negative"}
],
"embarrass": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"embarrassing": [
  {"sentiment": "negative"}
],
"embarrassment": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"embezzlement": [
  {"sentiment": "negative"}
],
"embolism": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"embrace": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"embroiled": [
  {"sentiment": "negative"}
],
"emergency": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"emeritus": [
  {"sentiment": "positive"}
],
"eminence": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"eminent": [
  {"sentiment": "positive"}
],
"eminently": [
  {"sentiment": "positive"}
],
"emir": [
  {"sentiment": "positive"}
],
"empathy": [
  {"sentiment": "positive"}
],
"emphasize": [
  {"sentiment": "trust"}
],
"employ": [
  {"sentiment": "trust"}
],
"empower": [
  {"sentiment": "positive"}
],
"emptiness": [
  {"sentiment": "sadness"}
],
"emulate": [
  {"sentiment": "positive"}
],
"enable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"enablement": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"enchant": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"enchanted": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"enchanting": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"enclave": [
  {"sentiment": "negative"}
],
"encore": [
  {"sentiment": "positive"}
],
"encourage": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"encouragement": [
  {"sentiment": "positive"}
],
"encroachment": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"encumbrance": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"encyclopedia": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"endanger": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"endangered": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"endeavor": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"endemic": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"endless": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"endocarditis": [
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"endow": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"endowed": [
  {"sentiment": "positive"}
],
"endowment": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"endurance": [
  {"sentiment": "positive"}
],
"endure": [
  {"sentiment": "positive"}
],
"enema": [
  {"sentiment": "disgust"}
],
"enemy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"energetic": [
  {"sentiment": "positive"}
],
"enforce": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"enforcement": [
  {"sentiment": "negative"}
],
"engaged": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"engaging": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"engulf": [
  {"sentiment": "anticipation"}
],
"enhance": [
  {"sentiment": "positive"}
],
"enigmatic": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"enjoy": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"enjoying": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"enlighten": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"enlightenment": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"enliven": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"enmity": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"enrich": [
  {"sentiment": "positive"}
],
"enroll": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"ensemble": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"ensign": [
  {"sentiment": "positive"}
],
"enslave": [
  {"sentiment": "negative"}
],
"enslaved": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"enslavement": [
  {"sentiment": "negative"}
],
"entangled": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"entanglement": [
  {"sentiment": "negative"}
],
"enterprising": [
  {"sentiment": "positive"}
],
"entertain": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"entertained": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"entertaining": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"entertainment": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"enthusiasm": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"enthusiast": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"entrails": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"entrust": [
  {"sentiment": "trust"}
],
"envious": [
  {"sentiment": "negative"}
],
"environ": [
  {"sentiment": "positive"}
],
"ephemeris": [
  {"sentiment": "positive"}
],
"epic": [
  {"sentiment": "positive"}
],
"epidemic": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"epilepsy": [
  {"sentiment": "negative"}
],
"episcopal": [
  {"sentiment": "trust"}
],
"epitaph": [
  {"sentiment": "sadness"}
],
"epitome": [
  {"sentiment": "positive"}
],
"equality": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"equally": [
  {"sentiment": "positive"}
],
"equilibrium": [
  {"sentiment": "positive"}
],
"equity": [
  {"sentiment": "positive"}
],
"eradicate": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"eradication": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"erase": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"erosion": [
  {"sentiment": "negative"}
],
"erotic": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"err": [
  {"sentiment": "negative"}
],
"errand": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"errant": [
  {"sentiment": "negative"}
],
"erratic": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"erratum": [
  {"sentiment": "negative"}
],
"erroneous": [
  {"sentiment": "negative"}
],
"error": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"erudite": [
  {"sentiment": "positive"}
],
"erupt": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"eruption": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"escalate": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"escape": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"escaped": [
  {"sentiment": "fear"}
],
"eschew": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"escort": [
  {"sentiment": "trust"}
],
"espionage": [
  {"sentiment": "negative"}
],
"esprit": [
  {"sentiment": "positive"}
],
"essential": [
  {"sentiment": "positive"}
],
"establish": [
  {"sentiment": "trust"}
],
"established": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"esteem": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"esthetic": [
  {"sentiment": "positive"}
],
"estranged": [
  {"sentiment": "negative"}
],
"ethereal": [
  {"sentiment": "fear"}
],
"ethical": [
  {"sentiment": "positive"}
],
"ethics": [
  {"sentiment": "positive"}
],
"euthanasia": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"evacuate": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"evacuation": [
  {"sentiment": "negative"}
],
"evade": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"evanescence": [
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"evasion": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"eventual": [
  {"sentiment": "anticipation"}
],
"eventuality": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"}
],
"evergreen": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"everlasting": [
  {"sentiment": "positive"}
],
"evict": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"eviction": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"evident": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"evil": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"evolution": [
  {"sentiment": "positive"}
],
"exacerbate": [
  {"sentiment": "negative"}
],
"exacerbation": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"exacting": [
  {"sentiment": "negative"}
],
"exaggerate": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"exaggerated": [
  {"sentiment": "negative"}
],
"exalt": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"exaltation": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"exalted": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"examination": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"exasperation": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"excavation": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"exceed": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"excel": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"excellence": [
  {"sentiment": "disgust"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"excellent": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"excess": [
  {"sentiment": "negative"}
],
"exchange": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"excise": [
  {"sentiment": "negative"}
],
"excitable": [
  {"sentiment": "positive"}
],
"excitation": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"excite": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"excited": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"excitement": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"exciting": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"exclaim": [
  {"sentiment": "surprise"}
],
"excluded": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"excluding": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"exclusion": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"excrement": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"excretion": [
  {"sentiment": "disgust"}
],
"excruciating": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"excuse": [
  {"sentiment": "negative"}
],
"execution": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"executioner": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"executor": [
  {"sentiment": "trust"}
],
"exemplary": [
  {"sentiment": "positive"}
],
"exemption": [
  {"sentiment": "positive"}
],
"exhaust": [
  {"sentiment": "negative"}
],
"exhausted": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"exhaustion": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"exhaustive": [
  {"sentiment": "trust"}
],
"exhilaration": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"exhort": [
  {"sentiment": "positive"}
],
"exhortation": [
  {"sentiment": "positive"}
],
"exigent": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"exile": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"existence": [
  {"sentiment": "positive"}
],
"exorcism": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"exotic": [
  {"sentiment": "positive"}
],
"expatriate": [
  {"sentiment": "negative"}
],
"expect": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"expectancy": [
  {"sentiment": "anticipation"}
],
"expectant": [
  {"sentiment": "anticipation"}
],
"expectation": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"expected": [
  {"sentiment": "anticipation"}
],
"expecting": [
  {"sentiment": "anticipation"}
],
"expedient": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"expedition": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"expel": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"expenditure": [
  {"sentiment": "negative"}
],
"expenses": [
  {"sentiment": "negative"}
],
"experienced": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"experiment": [
  {"sentiment": "anticipation"},
  {"sentiment": "surprise"}
],
"expert": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"expertise": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"expire": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"expired": [
  {"sentiment": "negative"}
],
"explain": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"expletive": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"explode": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"explore": [
  {"sentiment": "anticipation"}
],
"explosion": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"explosive": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"expose": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"}
],
"exposed": [
  {"sentiment": "negative"}
],
"expropriation": [
  {"sentiment": "negative"}
],
"expulsion": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"exquisite": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"exquisitely": [
  {"sentiment": "positive"}
],
"extend": [
  {"sentiment": "positive"}
],
"extensive": [
  {"sentiment": "positive"}
],
"exterminate": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"extermination": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"extinct": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"extinguish": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"extra": [
  {"sentiment": "positive"}
],
"extrajudicial": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"extraordinary": [
  {"sentiment": "positive"}
],
"extremity": [
  {"sentiment": "negative"}
],
"extricate": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"exuberance": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"eyewitness": [
  {"sentiment": "trust"}
],
"fabricate": [
  {"sentiment": "negative"}
],
"fabrication": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"facilitate": [
  {"sentiment": "positive"}
],
"fact": [
  {"sentiment": "trust"}
],
"facts": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"faculty": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"fade": [
  {"sentiment": "negative"}
],
"faeces": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"failing": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"failure": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fain": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"fainting": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"fair": [
  {"sentiment": "positive"}
],
"fairly": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"faith": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"faithful": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"faithless": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fake": [
  {"sentiment": "negative"}
],
"fall": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fallacious": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"fallacy": [
  {"sentiment": "negative"}
],
"fallible": [
  {"sentiment": "negative"}
],
"falling": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fallow": [
  {"sentiment": "negative"}
],
"falsehood": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"falsely": [
  {"sentiment": "negative"}
],
"falsification": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"falsify": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"falsity": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"falter": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"fame": [
  {"sentiment": "positive"}
],
"familiar": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"familiarity": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"famine": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"famous": [
  {"sentiment": "positive"}
],
"famously": [
  {"sentiment": "positive"}
],
"fanatic": [
  {"sentiment": "negative"}
],
"fanaticism": [
  {"sentiment": "fear"}
],
"fancy": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"fanfare": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"fang": [
  {"sentiment": "fear"}
],
"fangs": [
  {"sentiment": "fear"}
],
"farce": [
  {"sentiment": "negative"}
],
"farcical": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"farm": [
  {"sentiment": "anticipation"}
],
"fascinating": [
  {"sentiment": "positive"}
],
"fascination": [
  {"sentiment": "positive"}
],
"fashionable": [
  {"sentiment": "positive"}
],
"fasting": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fat": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fatal": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fatality": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fate": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"father": [
  {"sentiment": "trust"}
],
"fatigue": [
  {"sentiment": "negative"}
],
"fatigued": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fatty": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fault": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"faultless": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"faulty": [
  {"sentiment": "negative"}
],
"favorable": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"favorite": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"favoritism": [
  {"sentiment": "positive"}
],
"fawn": [
  {"sentiment": "negative"}
],
"fear": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"fearful": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fearfully": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"fearing": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"fearless": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"feat": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"feature": [
  {"sentiment": "positive"}
],
"febrile": [
  {"sentiment": "negative"}
],
"fecal": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"feces": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"fee": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"feeble": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"feeling": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"feigned": [
  {"sentiment": "negative"}
],
"felicity": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"fell": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fellow": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"fellowship": [
  {"sentiment": "positive"}
],
"felon": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"felony": [
  {"sentiment": "negative"}
],
"female": [
  {"sentiment": "positive"}
],
"fenced": [
  {"sentiment": "anger"}
],
"fender": [
  {"sentiment": "trust"}
],
"ferment": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"fermentation": [
  {"sentiment": "anticipation"}
],
"ferocious": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"ferocity": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"fertile": [
  {"sentiment": "positive"}
],
"fervor": [
  {"sentiment": "anger"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"festival": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"festive": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"fete": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"feud": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"feudal": [
  {"sentiment": "negative"}
],
"feudalism": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fever": [
  {"sentiment": "fear"}
],
"feverish": [
  {"sentiment": "negative"}
],
"fiasco": [
  {"sentiment": "negative"}
],
"fib": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"fickle": [
  {"sentiment": "negative"}
],
"fictitious": [
  {"sentiment": "negative"}
],
"fidelity": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"fiend": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"fierce": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"fiesta": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"fight": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"fighting": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"filibuster": [
  {"sentiment": "negative"}
],
"fill": [
  {"sentiment": "trust"}
],
"filth": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"filthy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"finally": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"finery": [
  {"sentiment": "positive"}
],
"finesse": [
  {"sentiment": "positive"}
],
"fire": [
  {"sentiment": "fear"}
],
"firearms": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"fireball": [
  {"sentiment": "positive"}
],
"fireman": [
  {"sentiment": "trust"}
],
"fireproof": [
  {"sentiment": "positive"}
],
"firmness": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"firstborn": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"fits": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"fitting": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"fixed": [
  {"sentiment": "trust"}
],
"fixture": [
  {"sentiment": "positive"}
],
"flabby": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"flaccid": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"flagging": [
  {"sentiment": "negative"}
],
"flagrant": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"flagship": [
  {"sentiment": "trust"}
],
"flake": [
  {"sentiment": "negative"}
],
"flange": [
  {"sentiment": "trust"}
],
"flap": [
  {"sentiment": "negative"}
],
"flattering": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"flatulence": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"flaunt": [
  {"sentiment": "negative"}
],
"flaw": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"flea": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"fled": [
  {"sentiment": "fear"}
],
"flee": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"fleece": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fleet": [
  {"sentiment": "positive"}
],
"flesh": [
  {"sentiment": "disgust"}
],
"fleshy": [
  {"sentiment": "negative"}
],
"flexibility": [
  {"sentiment": "positive"}
],
"flinch": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"flirt": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"flog": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"flood": [
  {"sentiment": "fear"}
],
"flop": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"floral": [
  {"sentiment": "positive"}
],
"flounder": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"flow": [
  {"sentiment": "positive"}
],
"flowery": [
  {"sentiment": "positive"}
],
"flu": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"fluctuation": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"fluke": [
  {"sentiment": "surprise"}
],
"flush": [
  {"sentiment": "positive"}
],
"flying": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"focus": [
  {"sentiment": "positive"}
],
"foe": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"foiled": [
  {"sentiment": "negative"}
],
"follower": [
  {"sentiment": "trust"}
],
"folly": [
  {"sentiment": "negative"}
],
"fondness": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"food": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"fool": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"foolish": [
  {"sentiment": "negative"}
],
"foolishness": [
  {"sentiment": "negative"}
],
"football": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"footing": [
  {"sentiment": "trust"}
],
"forage": [
  {"sentiment": "negative"}
],
"foray": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"forbearance": [
  {"sentiment": "positive"}
],
"forbid": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"forbidding": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"force": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"forced": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"forcibly": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"fore": [
  {"sentiment": "positive"}
],
"forearm": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"}
],
"foreboding": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"forecast": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"foreclose": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"forefathers": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"forego": [
  {"sentiment": "negative"}
],
"foregoing": [
  {"sentiment": "positive"}
],
"foreign": [
  {"sentiment": "negative"}
],
"foreigner": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"foreman": [
  {"sentiment": "positive"}
],
"forerunner": [
  {"sentiment": "positive"}
],
"foresee": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"foreseen": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"foresight": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"forewarned": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"forfeit": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"forfeited": [
  {"sentiment": "negative"}
],
"forfeiture": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"forge": [
  {"sentiment": "positive"}
],
"forgery": [
  {"sentiment": "negative"}
],
"forget": [
  {"sentiment": "negative"}
],
"forgive": [
  {"sentiment": "positive"}
],
"forgiven": [
  {"sentiment": "positive"}
],
"forgiving": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"forgotten": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"forlorn": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"formality": [
  {"sentiment": "trust"}
],
"formative": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"formidable": [
  {"sentiment": "fear"}
],
"forming": [
  {"sentiment": "anticipation"}
],
"formless": [
  {"sentiment": "negative"}
],
"formula": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"fornication": [
  {"sentiment": "negative"}
],
"forsake": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"forsaken": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fort": [
  {"sentiment": "trust"}
],
"forte": [
  {"sentiment": "positive"}
],
"forthcoming": [
  {"sentiment": "positive"}
],
"fortify": [
  {"sentiment": "positive"}
],
"fortitude": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"fortress": [
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"fortunate": [
  {"sentiment": "positive"}
],
"fortune": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"forward": [
  {"sentiment": "positive"}
],
"foul": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"found": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"foundation": [
  {"sentiment": "positive"}
],
"fracture": [
  {"sentiment": "negative"}
],
"fragile": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fragrant": [
  {"sentiment": "positive"}
],
"frailty": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"framework": [
  {"sentiment": "trust"}
],
"frank": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"frankness": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"frantic": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"fraternal": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"fraud": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"fraudulent": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"fraught": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fray": [
  {"sentiment": "negative"}
],
"frayed": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"freakish": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"freedom": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"freely": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"freezing": [
  {"sentiment": "negative"}
],
"frenetic": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"frenzied": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"frenzy": [
  {"sentiment": "negative"}
],
"fret": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"friction": [
  {"sentiment": "anger"}
],
"friend": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"friendliness": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"friendly": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"friendship": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"frigate": [
  {"sentiment": "fear"}
],
"fright": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"frighten": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"frightened": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"frightful": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"frigid": [
  {"sentiment": "negative"}
],
"frisky": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"frivolous": [
  {"sentiment": "negative"}
],
"frolic": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"frostbite": [
  {"sentiment": "negative"}
],
"froth": [
  {"sentiment": "negative"}
],
"frown": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"frowning": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"frugal": [
  {"sentiment": "positive"}
],
"fruitful": [
  {"sentiment": "positive"}
],
"fruitless": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"frustrate": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"frustrated": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"frustration": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"fudge": [
  {"sentiment": "negative"}
],
"fugitive": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"fulfill": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"fulfillment": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"full": [
  {"sentiment": "positive"}
],
"fully": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"fumble": [
  {"sentiment": "negative"}
],
"fume": [
  {"sentiment": "negative"}
],
"fuming": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"fun": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"fundamental": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"funeral": [
  {"sentiment": "sadness"}
],
"fungus": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"funk": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"furious": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"furiously": [
  {"sentiment": "anger"}
],
"furnace": [
  {"sentiment": "anger"}
],
"furor": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"furrow": [
  {"sentiment": "sadness"}
],
"fury": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fuse": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"fusion": [
  {"sentiment": "positive"}
],
"fuss": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"fussy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"futile": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"futility": [
  {"sentiment": "negative"}
],
"gaby": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"gag": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"gage": [
  {"sentiment": "trust"}
],
"gain": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"gaining": [
  {"sentiment": "positive"}
],
"gall": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"gallant": [
  {"sentiment": "positive"}
],
"gallantry": [
  {"sentiment": "positive"}
],
"gallows": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"galore": [
  {"sentiment": "positive"}
],
"gamble": [
  {"sentiment": "negative"}
],
"gambler": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"gambling": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"gang": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"gaol": [
  {"sentiment": "negative"}
],
"gap": [
  {"sentiment": "negative"}
],
"gape": [
  {"sentiment": "surprise"}
],
"garbage": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"garden": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"garish": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"garnet": [
  {"sentiment": "positive"}
],
"garnish": [
  {"sentiment": "negative"}
],
"garrison": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"gash": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"gasp": [
  {"sentiment": "surprise"}
],
"gasping": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"gate": [
  {"sentiment": "trust"}
],
"gateway": [
  {"sentiment": "trust"}
],
"gauche": [
  {"sentiment": "negative"}
],
"gauging": [
  {"sentiment": "trust"}
],
"gaunt": [
  {"sentiment": "negative"}
],
"gawk": [
  {"sentiment": "surprise"}
],
"gazette": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"gear": [
  {"sentiment": "positive"}
],
"gelatin": [
  {"sentiment": "disgust"}
],
"gem": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"general": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"generosity": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"generous": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"genial": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"genius": [
  {"sentiment": "positive"}
],
"gent": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"genteel": [
  {"sentiment": "positive"}
],
"gentleman": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"gentleness": [
  {"sentiment": "positive"}
],
"gentry": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"genuine": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"geranium": [
  {"sentiment": "positive"}
],
"geriatric": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"germ": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"}
],
"germination": [
  {"sentiment": "anticipation"}
],
"ghastly": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"ghetto": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ghost": [
  {"sentiment": "fear"}
],
"ghostly": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"giant": [
  {"sentiment": "fear"}
],
"gibberish": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"gift": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"gifted": [
  {"sentiment": "positive"}
],
"gigantic": [
  {"sentiment": "positive"}
],
"giggle": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"girder": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"giving": [
  {"sentiment": "positive"}
],
"glacial": [
  {"sentiment": "negative"}
],
"glad": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"gladiator": [
  {"sentiment": "fear"}
],
"gladness": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"glare": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"glaring": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"glee": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"glib": [
  {"sentiment": "negative"}
],
"glide": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"glimmer": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"glitter": [
  {"sentiment": "disgust"},
  {"sentiment": "joy"}
],
"glittering": [
  {"sentiment": "positive"}
],
"gloom": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"gloomy": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"glorification": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"glorify": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"glory": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"gloss": [
  {"sentiment": "positive"}
],
"glow": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"glowing": [
  {"sentiment": "positive"}
],
"glum": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"glut": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"gluttony": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"gnome": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"gob": [
  {"sentiment": "disgust"}
],
"goblin": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"god": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"godless": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"godly": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"godsend": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"gold": [
  {"sentiment": "positive"}
],
"gonorrhea": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"goo": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"good": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"goodly": [
  {"sentiment": "positive"}
],
"goodness": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"goods": [
  {"sentiment": "positive"}
],
"goodwill": [
  {"sentiment": "positive"}
],
"gore": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"gorge": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"gorgeous": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"gorilla": [
  {"sentiment": "negative"}
],
"gory": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"gospel": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"gossip": [
  {"sentiment": "negative"}
],
"gouge": [
  {"sentiment": "negative"}
],
"gout": [
  {"sentiment": "negative"}
],
"govern": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"governess": [
  {"sentiment": "trust"}
],
"government": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"governor": [
  {"sentiment": "trust"}
],
"grab": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"grace": [
  {"sentiment": "positive"}
],
"graceful": [
  {"sentiment": "positive"}
],
"gracious": [
  {"sentiment": "positive"}
],
"graciously": [
  {"sentiment": "positive"}
],
"gradual": [
  {"sentiment": "anticipation"}
],
"graduation": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"grammar": [
  {"sentiment": "trust"}
],
"grandchildren": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"grandeur": [
  {"sentiment": "positive"}
],
"grandfather": [
  {"sentiment": "trust"}
],
"grandmother": [
  {"sentiment": "positive"}
],
"grant": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"granted": [
  {"sentiment": "positive"}
],
"grasping": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"grate": [
  {"sentiment": "negative"}
],
"grated": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"grateful": [
  {"sentiment": "positive"}
],
"gratify": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"grating": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"gratitude": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"gratuitous": [
  {"sentiment": "disgust"}
],
"grave": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"gravitate": [
  {"sentiment": "anticipation"}
],
"gray": [
  {"sentiment": "disgust"},
  {"sentiment": "sadness"}
],
"greasy": [
  {"sentiment": "disgust"}
],
"greater": [
  {"sentiment": "positive"}
],
"greatness": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"greed": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"greedy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"green": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"greeting": [
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"gregarious": [
  {"sentiment": "positive"}
],
"grenade": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"grief": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"grievance": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"grieve": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"grievous": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"grim": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"grime": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"grimy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"grin": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"grinding": [
  {"sentiment": "negative"}
],
"grisly": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"grist": [
  {"sentiment": "positive"}
],
"grit": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"grizzly": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"groan": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"grope": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"gross": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"grotesque": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"ground": [
  {"sentiment": "trust"}
],
"grounded": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"groundless": [
  {"sentiment": "negative"}
],
"groundwork": [
  {"sentiment": "positive"}
],
"grow": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"growl": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"growling": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"growth": [
  {"sentiment": "positive"}
],
"grudge": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"grudgingly": [
  {"sentiment": "negative"}
],
"gruesome": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"gruff": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"grumble": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"grumpy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"guarantee": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"guard": [
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"guarded": [
  {"sentiment": "trust"}
],
"guardian": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"guardianship": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"gubernatorial": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"guerilla": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"guess": [
  {"sentiment": "surprise"}
],
"guidance": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"guide": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"guidebook": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"guile": [
  {"sentiment": "negative"}
],
"guillotine": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"guilt": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"guilty": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"guise": [
  {"sentiment": "negative"}
],
"gull": [
  {"sentiment": "negative"}
],
"gullible": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"gulp": [
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"gun": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"gunpowder": [
  {"sentiment": "fear"}
],
"guru": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"gush": [
  {"sentiment": "disgust"},
  {"sentiment": "joy"},
  {"sentiment": "negative"}
],
"gusset": [
  {"sentiment": "trust"}
],
"gut": [
  {"sentiment": "disgust"}
],
"guts": [
  {"sentiment": "positive"}
],
"gutter": [
  {"sentiment": "disgust"}
],
"guzzling": [
  {"sentiment": "negative"}
],
"gymnast": [
  {"sentiment": "positive"}
],
"gypsy": [
  {"sentiment": "negative"}
],
"habitat": [
  {"sentiment": "positive"}
],
"habitual": [
  {"sentiment": "anticipation"}
],
"hag": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"haggard": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hail": [
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"hairy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"hale": [
  {"sentiment": "positive"}
],
"halfway": [
  {"sentiment": "negative"}
],
"hallucination": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"halter": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"halting": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hamper": [
  {"sentiment": "negative"}
],
"hamstring": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"handbook": [
  {"sentiment": "trust"}
],
"handicap": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"handy": [
  {"sentiment": "positive"}
],
"hanging": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hangman": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hankering": [
  {"sentiment": "anticipation"}
],
"hap": [
  {"sentiment": "anticipation"},
  {"sentiment": "surprise"}
],
"happen": [
  {"sentiment": "anticipation"}
],
"happily": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"happiness": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"happy": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"harass": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"harassing": [
  {"sentiment": "anger"}
],
"harbinger": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"harbor": [
  {"sentiment": "trust"}
],
"hardened": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hardness": [
  {"sentiment": "negative"}
],
"hardship": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hardy": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"harlot": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"harm": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"harmful": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"harmoniously": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"harmony": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"harrowing": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"harry": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"harshness": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"harvest": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"hash": [
  {"sentiment": "negative"}
],
"hashish": [
  {"sentiment": "negative"}
],
"haste": [
  {"sentiment": "anticipation"}
],
"hasty": [
  {"sentiment": "negative"}
],
"hate": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hateful": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hating": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"hatred": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"haughty": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"haunt": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"haunted": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"haven": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"havoc": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hawk": [
  {"sentiment": "fear"}
],
"hazard": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hazardous": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"haze": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"headache": [
  {"sentiment": "negative"}
],
"headlight": [
  {"sentiment": "anticipation"}
],
"headway": [
  {"sentiment": "positive"}
],
"heady": [
  {"sentiment": "negative"}
],
"heal": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"healing": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"healthful": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"healthy": [
  {"sentiment": "positive"}
],
"hearing": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hearsay": [
  {"sentiment": "negative"}
],
"hearse": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"heartache": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"heartburn": [
  {"sentiment": "negative"}
],
"heartfelt": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"hearth": [
  {"sentiment": "positive"}
],
"heartily": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"heartless": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"heartworm": [
  {"sentiment": "disgust"}
],
"heathen": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"heavenly": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"heavens": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"heavily": [
  {"sentiment": "negative"}
],
"hedonism": [
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"heel": [
  {"sentiment": "negative"}
],
"heft": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"heighten": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"heinous": [
  {"sentiment": "negative"}
],
"hell": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hellish": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"helmet": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"helper": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"helpful": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"helpless": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"helplessness": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hemorrhage": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hemorrhoids": [
  {"sentiment": "negative"}
],
"herbal": [
  {"sentiment": "positive"}
],
"heresy": [
  {"sentiment": "negative"}
],
"heretic": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"heritage": [
  {"sentiment": "trust"}
],
"hermaphrodite": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"hermit": [
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"hero": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"heroic": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"heroics": [
  {"sentiment": "positive"}
],
"heroin": [
  {"sentiment": "negative"}
],
"heroine": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"heroism": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"herpes": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"herpesvirus": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"hesitation": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"heyday": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"hidden": [
  {"sentiment": "negative"}
],
"hide": [
  {"sentiment": "fear"}
],
"hideous": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hiding": [
  {"sentiment": "fear"}
],
"highest": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"hilarious": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"hilarity": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"hindering": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hindrance": [
  {"sentiment": "negative"}
],
"hippie": [
  {"sentiment": "negative"}
],
"hire": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"hiss": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hissing": [
  {"sentiment": "negative"}
],
"hit": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"hitherto": [
  {"sentiment": "negative"}
],
"hive": [
  {"sentiment": "negative"}
],
"hoarse": [
  {"sentiment": "negative"}
],
"hoary": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hoax": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"hobby": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"hobo": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hog": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"holiday": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"holiness": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"hollow": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"holocaust": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"holy": [
  {"sentiment": "positive"}
],
"homage": [
  {"sentiment": "positive"}
],
"homeless": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"homesick": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"homework": [
  {"sentiment": "fear"}
],
"homicidal": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"homicide": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"homology": [
  {"sentiment": "positive"}
],
"homosexuality": [
  {"sentiment": "negative"}
],
"honest": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"honesty": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"honey": [
  {"sentiment": "positive"}
],
"honeymoon": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"honor": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"honorable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"hood": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hooded": [
  {"sentiment": "fear"}
],
"hooked": [
  {"sentiment": "negative"}
],
"hoot": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"hope": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"hopeful": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"hopeless": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hopelessness": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"horde": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"horizon": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"horoscope": [
  {"sentiment": "anticipation"}
],
"horrible": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"horribly": [
  {"sentiment": "negative"}
],
"horrid": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"horrific": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"horrified": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"horrifying": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"horror": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"horrors": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"horse": [
  {"sentiment": "trust"}
],
"hospice": [
  {"sentiment": "sadness"}
],
"hospital": [
  {"sentiment": "fear"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"hospitality": [
  {"sentiment": "positive"}
],
"hostage": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hostile": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hostilities": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hostility": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"hot": [
  {"sentiment": "anger"}
],
"household": [
  {"sentiment": "positive"}
],
"housekeeping": [
  {"sentiment": "positive"}
],
"howl": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"huff": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"hug": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"hulk": [
  {"sentiment": "disgust"}
],
"humane": [
  {"sentiment": "positive"}
],
"humanitarian": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"humanity": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"humble": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"humbled": [
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"humbly": [
  {"sentiment": "positive"}
],
"humbug": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"humiliate": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"humiliating": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"humiliation": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"humility": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"humorist": [
  {"sentiment": "positive"}
],
"humorous": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"hunch": [
  {"sentiment": "negative"}
],
"hungry": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"hunter": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hunting": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hurrah": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"hurricane": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hurried": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"hurry": [
  {"sentiment": "anticipation"}
],
"hurt": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hurtful": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hurting": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"husbandry": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"hush": [
  {"sentiment": "positive"}
],
"hustler": [
  {"sentiment": "negative"}
],
"hut": [
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"hydra": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hydrocephalus": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"hygienic": [
  {"sentiment": "positive"}
],
"hymn": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"hype": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"hyperbole": [
  {"sentiment": "negative"}
],
"hypertrophy": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"hypocrisy": [
  {"sentiment": "negative"}
],
"hypocrite": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"hypocritical": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"hypothesis": [
  {"sentiment": "anticipation"},
  {"sentiment": "surprise"}
],
"hysteria": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"hysterical": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"idealism": [
  {"sentiment": "positive"}
],
"idiocy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"idiot": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"idiotic": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"idler": [
  {"sentiment": "negative"}
],
"idol": [
  {"sentiment": "positive"}
],
"idolatry": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"ignorance": [
  {"sentiment": "negative"}
],
"ignorant": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"ignore": [
  {"sentiment": "negative"}
],
"ill": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"illegal": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"illegality": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"illegible": [
  {"sentiment": "negative"}
],
"illegitimate": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"illicit": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"illiterate": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"illness": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"illogical": [
  {"sentiment": "negative"}
],
"illuminate": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"illumination": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"illusion": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"illustrate": [
  {"sentiment": "positive"}
],
"illustrious": [
  {"sentiment": "positive"}
],
"imaginative": [
  {"sentiment": "positive"}
],
"imitated": [
  {"sentiment": "negative"}
],
"imitation": [
  {"sentiment": "negative"}
],
"immaculate": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"immature": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"immaturity": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"immediacy": [
  {"sentiment": "surprise"}
],
"immediately": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"immense": [
  {"sentiment": "positive"}
],
"immerse": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"immigrant": [
  {"sentiment": "fear"}
],
"imminent": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"}
],
"immoral": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"immorality": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"immortal": [
  {"sentiment": "positive"}
],
"immortality": [
  {"sentiment": "anticipation"}
],
"immovable": [
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"immunization": [
  {"sentiment": "trust"}
],
"impair": [
  {"sentiment": "negative"}
],
"impairment": [
  {"sentiment": "negative"}
],
"impart": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"impartial": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"impartiality": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"impassable": [
  {"sentiment": "negative"}
],
"impatience": [
  {"sentiment": "negative"}
],
"impatient": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"impeach": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"impeachment": [
  {"sentiment": "negative"}
],
"impeccable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"impede": [
  {"sentiment": "negative"}
],
"impending": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"}
],
"impenetrable": [
  {"sentiment": "trust"}
],
"imperfection": [
  {"sentiment": "negative"}
],
"imperfectly": [
  {"sentiment": "negative"}
],
"impermeable": [
  {"sentiment": "anger"},
  {"sentiment": "fear"}
],
"impersonate": [
  {"sentiment": "negative"}
],
"impersonation": [
  {"sentiment": "negative"}
],
"impervious": [
  {"sentiment": "positive"}
],
"implacable": [
  {"sentiment": "negative"}
],
"implicate": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"impolite": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"importance": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"important": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"imposition": [
  {"sentiment": "negative"}
],
"impossible": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"impotence": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"impotent": [
  {"sentiment": "negative"}
],
"impound": [
  {"sentiment": "negative"}
],
"impracticable": [
  {"sentiment": "negative"}
],
"impress": [
  {"sentiment": "positive"}
],
"impression": [
  {"sentiment": "positive"}
],
"impressionable": [
  {"sentiment": "trust"}
],
"imprison": [
  {"sentiment": "negative"}
],
"imprisoned": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"imprisonment": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"impropriety": [
  {"sentiment": "negative"}
],
"improve": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"improved": [
  {"sentiment": "positive"}
],
"improvement": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"improving": [
  {"sentiment": "positive"}
],
"improvisation": [
  {"sentiment": "surprise"}
],
"improvise": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"imprudent": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"impure": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"impurity": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"imputation": [
  {"sentiment": "negative"}
],
"inability": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inaccessible": [
  {"sentiment": "negative"}
],
"inaccurate": [
  {"sentiment": "negative"}
],
"inaction": [
  {"sentiment": "negative"}
],
"inactivity": [
  {"sentiment": "negative"}
],
"inadequacy": [
  {"sentiment": "negative"}
],
"inadequate": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inadmissible": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"inalienable": [
  {"sentiment": "positive"}
],
"inane": [
  {"sentiment": "negative"}
],
"inapplicable": [
  {"sentiment": "negative"}
],
"inappropriate": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inattention": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"inaudible": [
  {"sentiment": "negative"}
],
"inaugural": [
  {"sentiment": "anticipation"}
],
"inauguration": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"incalculable": [
  {"sentiment": "negative"}
],
"incapacity": [
  {"sentiment": "negative"}
],
"incarceration": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"incase": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"incendiary": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"incense": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"incessant": [
  {"sentiment": "negative"}
],
"incest": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"incestuous": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"incident": [
  {"sentiment": "surprise"}
],
"incineration": [
  {"sentiment": "negative"}
],
"incisive": [
  {"sentiment": "positive"}
],
"incite": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"inclement": [
  {"sentiment": "negative"}
],
"incline": [
  {"sentiment": "trust"}
],
"include": [
  {"sentiment": "positive"}
],
"included": [
  {"sentiment": "positive"}
],
"including": [
  {"sentiment": "positive"}
],
"inclusion": [
  {"sentiment": "trust"}
],
"inclusive": [
  {"sentiment": "positive"}
],
"incoherent": [
  {"sentiment": "negative"}
],
"income": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"incompatible": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"incompetence": [
  {"sentiment": "negative"}
],
"incompetent": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"incompleteness": [
  {"sentiment": "negative"}
],
"incomprehensible": [
  {"sentiment": "negative"}
],
"incongruous": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"inconsequential": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inconsiderate": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inconsistency": [
  {"sentiment": "negative"}
],
"incontinence": [
  {"sentiment": "surprise"}
],
"inconvenient": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"incorrect": [
  {"sentiment": "negative"}
],
"increase": [
  {"sentiment": "positive"}
],
"incredulous": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"incrimination": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"incubus": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"incur": [
  {"sentiment": "negative"}
],
"incurable": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"incursion": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"indecency": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"}
],
"indecent": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"indecision": [
  {"sentiment": "negative"}
],
"indecisive": [
  {"sentiment": "negative"}
],
"indefensible": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"indelible": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"indemnify": [
  {"sentiment": "negative"}
],
"indemnity": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"indent": [
  {"sentiment": "trust"}
],
"indenture": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"independence": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"indestructible": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"indeterminate": [
  {"sentiment": "negative"}
],
"indict": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"indictment": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"indifference": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"indigent": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"indignant": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"indignation": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"indistinct": [
  {"sentiment": "negative"}
],
"individuality": [
  {"sentiment": "positive"}
],
"indivisible": [
  {"sentiment": "trust"}
],
"indoctrination": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"indolent": [
  {"sentiment": "negative"}
],
"indomitable": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"ineffable": [
  {"sentiment": "positive"}
],
"ineffective": [
  {"sentiment": "negative"}
],
"ineffectual": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"inefficiency": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inefficient": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inept": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"ineptitude": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inequality": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inequitable": [
  {"sentiment": "negative"}
],
"inert": [
  {"sentiment": "negative"}
],
"inexcusable": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inexpensive": [
  {"sentiment": "positive"}
],
"inexperience": [
  {"sentiment": "negative"}
],
"inexperienced": [
  {"sentiment": "negative"}
],
"inexplicable": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"infallibility": [
  {"sentiment": "trust"}
],
"infallible": [
  {"sentiment": "positive"}
],
"infamous": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"infamy": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"infant": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"infanticide": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"infantile": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"infarct": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"infect": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"infection": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"infectious": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inferior": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inferiority": [
  {"sentiment": "negative"}
],
"inferno": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"infertility": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"infestation": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"infidel": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"infidelity": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"infiltration": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"infinite": [
  {"sentiment": "positive"}
],
"infinity": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"infirm": [
  {"sentiment": "negative"}
],
"infirmity": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"inflammation": [
  {"sentiment": "negative"}
],
"inflated": [
  {"sentiment": "negative"}
],
"inflation": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"inflict": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"infliction": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"influence": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"influential": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"influenza": [
  {"sentiment": "negative"}
],
"inform": [
  {"sentiment": "trust"}
],
"information": [
  {"sentiment": "positive"}
],
"informer": [
  {"sentiment": "negative"}
],
"infraction": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"infrequent": [
  {"sentiment": "surprise"}
],
"infrequently": [
  {"sentiment": "negative"}
],
"infringement": [
  {"sentiment": "negative"}
],
"ingenious": [
  {"sentiment": "positive"}
],
"inheritance": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"inhibit": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inhospitable": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inhuman": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inhumanity": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inimical": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"inimitable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"iniquity": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"injection": [
  {"sentiment": "fear"}
],
"injunction": [
  {"sentiment": "negative"}
],
"injure": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"injured": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"injurious": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"injury": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"injustice": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"inmate": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"innocence": [
  {"sentiment": "positive"}
],
"innocent": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"innocently": [
  {"sentiment": "positive"}
],
"innocuous": [
  {"sentiment": "positive"}
],
"innovate": [
  {"sentiment": "positive"}
],
"innovation": [
  {"sentiment": "positive"}
],
"inoculation": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"inoperative": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"inquirer": [
  {"sentiment": "positive"}
],
"inquiry": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"inquisitive": [
  {"sentiment": "positive"}
],
"insane": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"insanity": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"insecure": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"insecurity": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"inseparable": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"insidious": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"insignia": [
  {"sentiment": "positive"}
],
"insignificance": [
  {"sentiment": "negative"}
],
"insignificant": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"insipid": [
  {"sentiment": "negative"}
],
"insolent": [
  {"sentiment": "negative"}
],
"insolvency": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"insolvent": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"inspector": [
  {"sentiment": "positive"}
],
"inspiration": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"inspire": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"inspired": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"instability": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"install": [
  {"sentiment": "anticipation"}
],
"instigate": [
  {"sentiment": "negative"}
],
"instigation": [
  {"sentiment": "negative"}
],
"instinctive": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"institute": [
  {"sentiment": "trust"}
],
"instruct": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"instruction": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"instructions": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"instructor": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"instrumental": [
  {"sentiment": "positive"}
],
"insufficiency": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"insufficient": [
  {"sentiment": "negative"}
],
"insufficiently": [
  {"sentiment": "negative"}
],
"insulation": [
  {"sentiment": "trust"}
],
"insult": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"insulting": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"insure": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"insurgent": [
  {"sentiment": "negative"}
],
"insurmountable": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"insurrection": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"intact": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"integrity": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"intellect": [
  {"sentiment": "positive"}
],
"intellectual": [
  {"sentiment": "positive"}
],
"intelligence": [
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"intelligent": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"intend": [
  {"sentiment": "trust"}
],
"intended": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"intense": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"inter": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"intercede": [
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"intercession": [
  {"sentiment": "trust"}
],
"intercourse": [
  {"sentiment": "positive"}
],
"interdiction": [
  {"sentiment": "negative"}
],
"interest": [
  {"sentiment": "positive"}
],
"interested": [
  {"sentiment": "disgust"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"interesting": [
  {"sentiment": "positive"}
],
"interference": [
  {"sentiment": "negative"}
],
"interim": [
  {"sentiment": "anticipation"}
],
"interior": [
  {"sentiment": "disgust"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"interlocutory": [
  {"sentiment": "positive"}
],
"interlude": [
  {"sentiment": "positive"}
],
"interment": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"interminable": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"intermission": [
  {"sentiment": "anticipation"}
],
"interrogate": [
  {"sentiment": "fear"}
],
"interrogation": [
  {"sentiment": "fear"}
],
"interrupt": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"interrupted": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"intervention": [
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"interviewer": [
  {"sentiment": "fear"}
],
"intestate": [
  {"sentiment": "negative"}
],
"intestinal": [
  {"sentiment": "disgust"}
],
"intimate": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"intimately": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"}
],
"intimidate": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"intimidation": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"intolerable": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"intolerance": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"intolerant": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"intonation": [
  {"sentiment": "positive"}
],
"intoxicated": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"intractable": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"intrepid": [
  {"sentiment": "positive"}
],
"intrigue": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"intruder": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"intrusion": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"intrusive": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"intuition": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"intuitive": [
  {"sentiment": "positive"}
],
"intuitively": [
  {"sentiment": "anticipation"}
],
"invade": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"invader": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"invalid": [
  {"sentiment": "sadness"}
],
"invalidate": [
  {"sentiment": "negative"}
],
"invalidation": [
  {"sentiment": "negative"}
],
"invalidity": [
  {"sentiment": "negative"}
],
"invariably": [
  {"sentiment": "positive"}
],
"invasion": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"inventive": [
  {"sentiment": "positive"}
],
"inventor": [
  {"sentiment": "positive"}
],
"investigate": [
  {"sentiment": "positive"}
],
"investigation": [
  {"sentiment": "anticipation"}
],
"invigorate": [
  {"sentiment": "positive"}
],
"invitation": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"invite": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"inviting": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"invocation": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"invoke": [
  {"sentiment": "anticipation"}
],
"involuntary": [
  {"sentiment": "negative"}
],
"involution": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"involvement": [
  {"sentiment": "anger"}
],
"irate": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"ire": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"iris": [
  {"sentiment": "fear"}
],
"iron": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"irons": [
  {"sentiment": "negative"}
],
"irrational": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"irrationality": [
  {"sentiment": "negative"}
],
"irreconcilable": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"irreducible": [
  {"sentiment": "positive"}
],
"irrefutable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"irregular": [
  {"sentiment": "negative"}
],
"irregularity": [
  {"sentiment": "negative"}
],
"irrelevant": [
  {"sentiment": "negative"}
],
"irreparable": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"irresponsible": [
  {"sentiment": "negative"}
],
"irreverent": [
  {"sentiment": "negative"}
],
"irrevocable": [
  {"sentiment": "negative"}
],
"irritability": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"irritable": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"irritating": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"irritation": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"isolate": [
  {"sentiment": "sadness"}
],
"isolated": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"isolation": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"jab": [
  {"sentiment": "anger"}
],
"jabber": [
  {"sentiment": "negative"}
],
"jackpot": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"jail": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"jam": [
  {"sentiment": "positive"}
],
"janitor": [
  {"sentiment": "disgust"}
],
"jargon": [
  {"sentiment": "negative"}
],
"jarring": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"jaundice": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"jaws": [
  {"sentiment": "fear"}
],
"jealous": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"jealousy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"jeopardize": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"jeopardy": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"jerk": [
  {"sentiment": "anger"},
  {"sentiment": "surprise"}
],
"jest": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"job": [
  {"sentiment": "positive"}
],
"john": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"join": [
  {"sentiment": "positive"}
],
"joined": [
  {"sentiment": "positive"}
],
"joke": [
  {"sentiment": "negative"}
],
"joker": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"joking": [
  {"sentiment": "positive"}
],
"jolt": [
  {"sentiment": "surprise"}
],
"jornada": [
  {"sentiment": "negative"}
],
"journalism": [
  {"sentiment": "trust"}
],
"journalist": [
  {"sentiment": "positive"}
],
"journey": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"journeyman": [
  {"sentiment": "trust"}
],
"jovial": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"joy": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"joyful": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"joyous": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"jubilant": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"jubilee": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"judgment": [
  {"sentiment": "surprise"}
],
"judicial": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"judiciary": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"judicious": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"jumble": [
  {"sentiment": "negative"}
],
"jump": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"jungle": [
  {"sentiment": "fear"}
],
"junk": [
  {"sentiment": "negative"}
],
"junta": [
  {"sentiment": "negative"}
],
"jurisprudence": [
  {"sentiment": "sadness"}
],
"jurist": [
  {"sentiment": "trust"}
],
"jury": [
  {"sentiment": "trust"}
],
"justice": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"justifiable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"justification": [
  {"sentiment": "positive"}
],
"juvenile": [
  {"sentiment": "negative"}
],
"keepsake": [
  {"sentiment": "positive"}
],
"ken": [
  {"sentiment": "positive"}
],
"kennel": [
  {"sentiment": "sadness"}
],
"kern": [
  {"sentiment": "negative"}
],
"kerosene": [
  {"sentiment": "fear"}
],
"keynote": [
  {"sentiment": "positive"}
],
"keystone": [
  {"sentiment": "positive"}
],
"khan": [
  {"sentiment": "fear"},
  {"sentiment": "trust"}
],
"kick": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"kicking": [
  {"sentiment": "anger"}
],
"kidnap": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"kill": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"killing": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"kind": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"kindness": [
  {"sentiment": "positive"}
],
"kindred": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"king": [
  {"sentiment": "positive"}
],
"kiss": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"kite": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"kitten": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"knack": [
  {"sentiment": "positive"}
],
"knell": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"knickers": [
  {"sentiment": "trust"}
],
"knight": [
  {"sentiment": "positive"}
],
"knotted": [
  {"sentiment": "negative"}
],
"knowing": [
  {"sentiment": "positive"}
],
"knowledge": [
  {"sentiment": "positive"}
],
"kudos": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"label": [
  {"sentiment": "trust"}
],
"labor": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"labored": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"laborious": [
  {"sentiment": "negative"}
],
"labyrinth": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"lace": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"lack": [
  {"sentiment": "negative"}
],
"lacking": [
  {"sentiment": "negative"}
],
"lackluster": [
  {"sentiment": "negative"}
],
"laden": [
  {"sentiment": "negative"}
],
"lag": [
  {"sentiment": "negative"}
],
"lagging": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lair": [
  {"sentiment": "negative"}
],
"lamb": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"lament": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lamenting": [
  {"sentiment": "sadness"}
],
"land": [
  {"sentiment": "positive"}
],
"landed": [
  {"sentiment": "positive"}
],
"landmark": [
  {"sentiment": "trust"}
],
"landslide": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"languid": [
  {"sentiment": "negative"}
],
"languish": [
  {"sentiment": "negative"}
],
"languishing": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lapse": [
  {"sentiment": "negative"}
],
"larceny": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"larger": [
  {"sentiment": "disgust"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"laser": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"lash": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"late": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lateness": [
  {"sentiment": "negative"}
],
"latent": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"latrines": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"laudable": [
  {"sentiment": "positive"}
],
"laugh": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"laughable": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"laughing": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"laughter": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"launch": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"laureate": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"laurel": [
  {"sentiment": "positive"}
],
"laurels": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"lava": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"lavatory": [
  {"sentiment": "disgust"}
],
"lavish": [
  {"sentiment": "positive"}
],
"law": [
  {"sentiment": "trust"}
],
"lawful": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"lawlessness": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"lawsuit": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"lawyer": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"lax": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"laxative": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"lazy": [
  {"sentiment": "negative"}
],
"lead": [
  {"sentiment": "positive"}
],
"leader": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"leading": [
  {"sentiment": "trust"}
],
"league": [
  {"sentiment": "positive"}
],
"leak": [
  {"sentiment": "negative"}
],
"leakage": [
  {"sentiment": "negative"}
],
"leaky": [
  {"sentiment": "negative"}
],
"leaning": [
  {"sentiment": "trust"}
],
"learn": [
  {"sentiment": "positive"}
],
"learning": [
  {"sentiment": "positive"}
],
"leave": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"lecturer": [
  {"sentiment": "positive"}
],
"leech": [
  {"sentiment": "negative"}
],
"leeches": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"leer": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"leery": [
  {"sentiment": "surprise"}
],
"leeway": [
  {"sentiment": "positive"}
],
"legal": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"legalized": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"legendary": [
  {"sentiment": "positive"}
],
"legibility": [
  {"sentiment": "positive"}
],
"legible": [
  {"sentiment": "positive"}
],
"legislator": [
  {"sentiment": "trust"}
],
"legislature": [
  {"sentiment": "trust"}
],
"legitimacy": [
  {"sentiment": "trust"}
],
"leisure": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"leisurely": [
  {"sentiment": "positive"}
],
"lemma": [
  {"sentiment": "positive"}
],
"lemon": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"lender": [
  {"sentiment": "trust"}
],
"lenient": [
  {"sentiment": "positive"}
],
"leprosy": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lesbian": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lessen": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"lesser": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"lesson": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"lethal": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lethargy": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"letter": [
  {"sentiment": "anticipation"}
],
"lettered": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"leukemia": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"levee": [
  {"sentiment": "trust"}
],
"level": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"leverage": [
  {"sentiment": "positive"}
],
"levy": [
  {"sentiment": "negative"}
],
"lewd": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"liaison": [
  {"sentiment": "negative"}
],
"liar": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"libel": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"liberal": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"liberate": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"liberation": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"liberty": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"library": [
  {"sentiment": "positive"}
],
"lick": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"lie": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lieutenant": [
  {"sentiment": "trust"}
],
"lifeblood": [
  {"sentiment": "positive"}
],
"lifeless": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lighthouse": [
  {"sentiment": "positive"}
],
"lightning": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"liking": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"limited": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"limp": [
  {"sentiment": "negative"}
],
"lines": [
  {"sentiment": "fear"}
],
"linger": [
  {"sentiment": "anticipation"}
],
"linguist": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"lint": [
  {"sentiment": "negative"}
],
"lion": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"liquor": [
  {"sentiment": "anger"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"listless": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lithe": [
  {"sentiment": "positive"}
],
"litigant": [
  {"sentiment": "negative"}
],
"litigate": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"litigation": [
  {"sentiment": "negative"}
],
"litigious": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"litter": [
  {"sentiment": "negative"}
],
"livid": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"loaf": [
  {"sentiment": "negative"}
],
"loafer": [
  {"sentiment": "negative"}
],
"loath": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"loathe": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"loathing": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"loathsome": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"lobbyist": [
  {"sentiment": "negative"}
],
"localize": [
  {"sentiment": "anticipation"}
],
"lockup": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"locust": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"lodging": [
  {"sentiment": "trust"}
],
"lofty": [
  {"sentiment": "negative"}
],
"logical": [
  {"sentiment": "positive"}
],
"lone": [
  {"sentiment": "sadness"}
],
"loneliness": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lonely": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lonesome": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"long": [
  {"sentiment": "anticipation"}
],
"longevity": [
  {"sentiment": "positive"}
],
"longing": [
  {"sentiment": "anticipation"},
  {"sentiment": "sadness"}
],
"loo": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"loom": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"loon": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"loony": [
  {"sentiment": "negative"}
],
"loot": [
  {"sentiment": "negative"}
],
"lord": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"lordship": [
  {"sentiment": "positive"}
],
"lose": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"losing": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"loss": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lost": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lottery": [
  {"sentiment": "anticipation"}
],
"loudness": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"lounge": [
  {"sentiment": "negative"}
],
"louse": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"lovable": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"love": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"lovely": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"lovemaking": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"lover": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"loving": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"lower": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lowering": [
  {"sentiment": "negative"}
],
"lowest": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lowlands": [
  {"sentiment": "negative"}
],
"lowly": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"loyal": [
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"loyalty": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"luck": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"lucky": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"ludicrous": [
  {"sentiment": "negative"}
],
"lull": [
  {"sentiment": "anticipation"}
],
"lumbering": [
  {"sentiment": "negative"}
],
"lump": [
  {"sentiment": "negative"}
],
"lumpy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"lunacy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lunatic": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"lunge": [
  {"sentiment": "surprise"}
],
"lurch": [
  {"sentiment": "negative"}
],
"lure": [
  {"sentiment": "negative"}
],
"lurid": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"lurk": [
  {"sentiment": "negative"}
],
"lurking": [
  {"sentiment": "negative"}
],
"luscious": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"lush": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lust": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"luster": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"lustful": [
  {"sentiment": "negative"}
],
"lustrous": [
  {"sentiment": "positive"}
],
"lusty": [
  {"sentiment": "disgust"}
],
"luxuriant": [
  {"sentiment": "positive"}
],
"luxurious": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"luxury": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"lying": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"lynch": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"lyre": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"lyrical": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"mace": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"machine": [
  {"sentiment": "trust"}
],
"mad": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"madden": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"madman": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"madness": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"mafia": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"mage": [
  {"sentiment": "fear"}
],
"maggot": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"magical": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"magician": [
  {"sentiment": "surprise"}
],
"magnet": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"magnetism": [
  {"sentiment": "positive"}
],
"magnetite": [
  {"sentiment": "positive"}
],
"magnificence": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"magnificent": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"maiden": [
  {"sentiment": "positive"}
],
"mail": [
  {"sentiment": "anticipation"}
],
"main": [
  {"sentiment": "positive"}
],
"mainstay": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"maintenance": [
  {"sentiment": "trust"}
],
"majestic": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"majesty": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"major": [
  {"sentiment": "positive"}
],
"majority": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"makeshift": [
  {"sentiment": "negative"}
],
"malady": [
  {"sentiment": "negative"}
],
"malaise": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"malaria": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"malevolent": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"malfeasance": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"malformation": [
  {"sentiment": "negative"}
],
"malice": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"malicious": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"malign": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"malignancy": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"malignant": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"malpractice": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"mamma": [
  {"sentiment": "trust"}
],
"manage": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"management": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"mandamus": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"mandarin": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"mange": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"mangle": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"manhood": [
  {"sentiment": "positive"}
],
"mania": [
  {"sentiment": "negative"}
],
"maniac": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"maniacal": [
  {"sentiment": "negative"}
],
"manifestation": [
  {"sentiment": "fear"}
],
"manifested": [
  {"sentiment": "positive"}
],
"manipulate": [
  {"sentiment": "negative"}
],
"manipulation": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"manly": [
  {"sentiment": "positive"}
],
"manna": [
  {"sentiment": "positive"}
],
"mannered": [
  {"sentiment": "positive"}
],
"manners": [
  {"sentiment": "positive"}
],
"manslaughter": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"manual": [
  {"sentiment": "trust"}
],
"manufacturer": [
  {"sentiment": "positive"}
],
"manure": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"mar": [
  {"sentiment": "negative"}
],
"march": [
  {"sentiment": "positive"}
],
"margin": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"marine": [
  {"sentiment": "trust"}
],
"marked": [
  {"sentiment": "positive"}
],
"marketable": [
  {"sentiment": "positive"}
],
"maroon": [
  {"sentiment": "negative"}
],
"marquis": [
  {"sentiment": "positive"}
],
"marriage": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"marrow": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"marry": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"marshal": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"martial": [
  {"sentiment": "anger"}
],
"martingale": [
  {"sentiment": "negative"}
],
"martyr": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"martyrdom": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"marvel": [
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"marvelous": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"marvelously": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"masculine": [
  {"sentiment": "positive"}
],
"masochism": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"massacre": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"massage": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"master": [
  {"sentiment": "positive"}
],
"masterpiece": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"mastery": [
  {"sentiment": "anger"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"matchmaker": [
  {"sentiment": "anticipation"}
],
"mate": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"materialism": [
  {"sentiment": "negative"}
],
"materialist": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"maternal": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"mathematical": [
  {"sentiment": "trust"}
],
"matrimony": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"matron": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"mausoleum": [
  {"sentiment": "sadness"}
],
"maxim": [
  {"sentiment": "trust"}
],
"maximum": [
  {"sentiment": "positive"}
],
"mayor": [
  {"sentiment": "positive"}
],
"meadow": [
  {"sentiment": "positive"}
],
"meandering": [
  {"sentiment": "negative"}
],
"meaningless": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"measles": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"measure": [
  {"sentiment": "trust"}
],
"measured": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"medal": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"meddle": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"mediate": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"mediation": [
  {"sentiment": "positive"}
],
"mediator": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"medical": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"mediocre": [
  {"sentiment": "negative"}
],
"mediocrity": [
  {"sentiment": "negative"}
],
"meditate": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"mediterranean": [
  {"sentiment": "positive"}
],
"medley": [
  {"sentiment": "positive"}
],
"meek": [
  {"sentiment": "sadness"}
],
"melancholic": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"melancholy": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"melee": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"melodrama": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"meltdown": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"memento": [
  {"sentiment": "positive"}
],
"memorable": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"memorials": [
  {"sentiment": "sadness"}
],
"menace": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"menacing": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"mending": [
  {"sentiment": "positive"}
],
"menial": [
  {"sentiment": "negative"}
],
"menses": [
  {"sentiment": "positive"}
],
"mentor": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"mercenary": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"merchant": [
  {"sentiment": "trust"}
],
"merciful": [
  {"sentiment": "positive"}
],
"merciless": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"mercy": [
  {"sentiment": "positive"}
],
"merge": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"merit": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"meritorious": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"merriment": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"merry": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"mess": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"messenger": [
  {"sentiment": "trust"}
],
"messy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"metastasis": [
  {"sentiment": "negative"}
],
"methanol": [
  {"sentiment": "negative"}
],
"metropolitan": [
  {"sentiment": "positive"}
],
"mettle": [
  {"sentiment": "positive"}
],
"microscope": [
  {"sentiment": "trust"}
],
"microscopy": [
  {"sentiment": "positive"}
],
"midwife": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"midwifery": [
  {"sentiment": "positive"}
],
"mighty": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"mildew": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"military": [
  {"sentiment": "fear"}
],
"militia": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"mill": [
  {"sentiment": "anticipation"}
],
"mime": [
  {"sentiment": "positive"}
],
"mimicry": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"mindful": [
  {"sentiment": "positive"}
],
"mindfulness": [
  {"sentiment": "positive"}
],
"minimize": [
  {"sentiment": "negative"}
],
"minimum": [
  {"sentiment": "negative"}
],
"ministry": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"minority": [
  {"sentiment": "negative"}
],
"miracle": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"miraculous": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"mire": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"mirth": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"misbehavior": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"miscarriage": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"mischief": [
  {"sentiment": "negative"}
],
"mischievous": [
  {"sentiment": "negative"}
],
"misconception": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"misconduct": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"miserable": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"miserably": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"misery": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"misfortune": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"misguided": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"mishap": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"misinterpretation": [
  {"sentiment": "negative"}
],
"mislead": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"misleading": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"mismanagement": [
  {"sentiment": "negative"}
],
"mismatch": [
  {"sentiment": "negative"}
],
"misnomer": [
  {"sentiment": "negative"}
],
"misplace": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"misplaced": [
  {"sentiment": "negative"}
],
"misrepresent": [
  {"sentiment": "negative"}
],
"misrepresentation": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"misrepresented": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"missile": [
  {"sentiment": "fear"}
],
"missing": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"missionary": [
  {"sentiment": "positive"}
],
"misstatement": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"mistake": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"mistaken": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"mistress": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"mistrust": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"misunderstand": [
  {"sentiment": "negative"}
],
"misunderstanding": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"misuse": [
  {"sentiment": "negative"}
],
"mite": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"moan": [
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"moat": [
  {"sentiment": "trust"}
],
"mob": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"mobile": [
  {"sentiment": "anticipation"}
],
"mockery": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"mocking": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"model": [
  {"sentiment": "positive"}
],
"moderate": [
  {"sentiment": "positive"}
],
"moderator": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"modest": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"modesty": [
  {"sentiment": "positive"}
],
"modify": [
  {"sentiment": "surprise"}
],
"molestation": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"momentum": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"monetary": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"money": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"monk": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"monochrome": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"monogamy": [
  {"sentiment": "trust"}
],
"monopolist": [
  {"sentiment": "negative"}
],
"monsoon": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"monster": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"monstrosity": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"monument": [
  {"sentiment": "positive"}
],
"moody": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"moorings": [
  {"sentiment": "trust"}
],
"moot": [
  {"sentiment": "negative"}
],
"moral": [
  {"sentiment": "anger"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"morality": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"morals": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"morbid": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"morbidity": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"morgue": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"moribund": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"morn": [
  {"sentiment": "anticipation"}
],
"moron": [
  {"sentiment": "negative"}
],
"moronic": [
  {"sentiment": "negative"}
],
"morrow": [
  {"sentiment": "anticipation"}
],
"morsel": [
  {"sentiment": "negative"}
],
"mortal": [
  {"sentiment": "negative"}
],
"mortality": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"mortar": [
  {"sentiment": "positive"}
],
"mortgage": [
  {"sentiment": "fear"}
],
"mortgagee": [
  {"sentiment": "trust"}
],
"mortgagor": [
  {"sentiment": "fear"}
],
"mortification": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"mortuary": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"mosque": [
  {"sentiment": "anger"}
],
"mosquito": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"mother": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"motherhood": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"motion": [
  {"sentiment": "anticipation"}
],
"motive": [
  {"sentiment": "positive"}
],
"mountain": [
  {"sentiment": "anticipation"}
],
"mourn": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"mournful": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"mourning": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"mouth": [
  {"sentiment": "surprise"}
],
"mouthful": [
  {"sentiment": "disgust"}
],
"movable": [
  {"sentiment": "positive"}
],
"mover": [
  {"sentiment": "positive"}
],
"muck": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"mucous": [
  {"sentiment": "disgust"}
],
"mucus": [
  {"sentiment": "disgust"}
],
"mud": [
  {"sentiment": "negative"}
],
"muddle": [
  {"sentiment": "negative"}
],
"muddled": [
  {"sentiment": "negative"}
],
"muddy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"muff": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"mug": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"mule": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"mum": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"mumble": [
  {"sentiment": "negative"}
],
"mumps": [
  {"sentiment": "negative"}
],
"murder": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"murderer": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"murderous": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"murky": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"muscular": [
  {"sentiment": "positive"}
],
"music": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"musical": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"musket": [
  {"sentiment": "fear"}
],
"muss": [
  {"sentiment": "negative"}
],
"musty": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"mutable": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"mutant": [
  {"sentiment": "negative"}
],
"mutilated": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"mutilation": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"mutiny": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"mutter": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"mutual": [
  {"sentiment": "positive"}
],
"muzzle": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"myopia": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"mysterious": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"mystery": [
  {"sentiment": "anticipation"},
  {"sentiment": "surprise"}
],
"mystic": [
  {"sentiment": "surprise"}
],
"nab": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"nadir": [
  {"sentiment": "negative"}
],
"nag": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"naive": [
  {"sentiment": "negative"}
],
"nameless": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"nap": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"napkin": [
  {"sentiment": "sadness"}
],
"nappy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"narcotic": [
  {"sentiment": "negative"}
],
"nascent": [
  {"sentiment": "anticipation"}
],
"nasty": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"nation": [
  {"sentiment": "trust"}
],
"naturalist": [
  {"sentiment": "positive"}
],
"naughty": [
  {"sentiment": "negative"}
],
"nausea": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"nauseous": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"navigable": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"navigator": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"nay": [
  {"sentiment": "negative"}
],
"neatly": [
  {"sentiment": "positive"}
],
"necessity": [
  {"sentiment": "sadness"}
],
"nectar": [
  {"sentiment": "positive"}
],
"needful": [
  {"sentiment": "negative"}
],
"needle": [
  {"sentiment": "positive"}
],
"needy": [
  {"sentiment": "negative"}
],
"nefarious": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"negation": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"negative": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"neglect": [
  {"sentiment": "negative"}
],
"neglected": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"neglecting": [
  {"sentiment": "negative"}
],
"negligence": [
  {"sentiment": "negative"}
],
"negligent": [
  {"sentiment": "negative"}
],
"negligently": [
  {"sentiment": "negative"}
],
"negotiate": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"negotiator": [
  {"sentiment": "positive"}
],
"negro": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"neighbor": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"neighborhood": [
  {"sentiment": "anticipation"}
],
"nepotism": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"nerve": [
  {"sentiment": "positive"}
],
"nervous": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"nervousness": [
  {"sentiment": "fear"}
],
"nest": [
  {"sentiment": "trust"}
],
"nestle": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"nether": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"nettle": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"network": [
  {"sentiment": "anticipation"}
],
"neuralgia": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"neurosis": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"neurotic": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"neutral": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"neutrality": [
  {"sentiment": "trust"}
],
"newcomer": [
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"nicotine": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"nigger": [
  {"sentiment": "negative"}
],
"nightmare": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"nihilism": [
  {"sentiment": "negative"}
],
"nobility": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"noble": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"nobleman": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"noise": [
  {"sentiment": "negative"}
],
"noisy": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"nomination": [
  {"sentiment": "positive"}
],
"noncompliance": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"nonsense": [
  {"sentiment": "negative"}
],
"nonsensical": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"noose": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"normality": [
  {"sentiment": "positive"}
],
"nose": [
  {"sentiment": "disgust"}
],
"notable": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"notables": [
  {"sentiment": "positive"}
],
"notary": [
  {"sentiment": "trust"}
],
"noted": [
  {"sentiment": "positive"}
],
"nothingness": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"notification": [
  {"sentiment": "anticipation"}
],
"notion": [
  {"sentiment": "positive"}
],
"notoriety": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"nourishment": [
  {"sentiment": "positive"}
],
"noxious": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"nuisance": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"nul": [
  {"sentiment": "negative"}
],
"nullify": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"numb": [
  {"sentiment": "negative"}
],
"numbers": [
  {"sentiment": "positive"}
],
"numbness": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"nun": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"nurse": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"nursery": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"nurture": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"nutritious": [
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"oaf": [
  {"sentiment": "negative"}
],
"oak": [
  {"sentiment": "positive"}
],
"oasis": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"oath": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"obedience": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"obese": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"obesity": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"obey": [
  {"sentiment": "fear"},
  {"sentiment": "trust"}
],
"obi": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"obit": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"obituary": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"objection": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"objectionable": [
  {"sentiment": "negative"}
],
"objective": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"oblige": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"obliging": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"obligor": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"obliterate": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"obliterated": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"obliteration": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"oblivion": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"obnoxious": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"obscene": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"obscenity": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"obscurity": [
  {"sentiment": "negative"}
],
"observant": [
  {"sentiment": "positive"}
],
"obstacle": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"obstetrician": [
  {"sentiment": "trust"}
],
"obstinate": [
  {"sentiment": "negative"}
],
"obstruct": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"obstruction": [
  {"sentiment": "negative"}
],
"obstructive": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"obtainable": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"obtuse": [
  {"sentiment": "negative"}
],
"obvious": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"occasional": [
  {"sentiment": "surprise"}
],
"occult": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"occupant": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"occupation": [
  {"sentiment": "positive"}
],
"occupy": [
  {"sentiment": "positive"}
],
"octopus": [
  {"sentiment": "disgust"}
],
"oddity": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"odious": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"odor": [
  {"sentiment": "negative"}
],
"offend": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"offended": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"offender": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"offense": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"offensive": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"offer": [
  {"sentiment": "positive"}
],
"offering": [
  {"sentiment": "trust"}
],
"offhand": [
  {"sentiment": "negative"}
],
"officer": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"official": [
  {"sentiment": "trust"}
],
"offing": [
  {"sentiment": "negative"}
],
"offset": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"offshoot": [
  {"sentiment": "positive"}
],
"ogre": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"older": [
  {"sentiment": "sadness"}
],
"olfactory": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"oligarchy": [
  {"sentiment": "negative"}
],
"omen": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"ominous": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"omit": [
  {"sentiment": "negative"}
],
"omnipotence": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"omniscient": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"onerous": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ongoing": [
  {"sentiment": "anticipation"}
],
"onset": [
  {"sentiment": "anticipation"}
],
"onus": [
  {"sentiment": "negative"}
],
"ooze": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"opaque": [
  {"sentiment": "negative"}
],
"openness": [
  {"sentiment": "positive"}
],
"opera": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"operatic": [
  {"sentiment": "negative"}
],
"operation": [
  {"sentiment": "fear"},
  {"sentiment": "trust"}
],
"opiate": [
  {"sentiment": "negative"}
],
"opinionated": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"opium": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"opponent": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"opportune": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"opportunity": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"oppose": [
  {"sentiment": "negative"}
],
"opposed": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"opposition": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"oppress": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"oppression": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"oppressive": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"oppressor": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"optimism": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"optimist": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"option": [
  {"sentiment": "positive"}
],
"optional": [
  {"sentiment": "positive"}
],
"oracle": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"oration": [
  {"sentiment": "positive"}
],
"orc": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"orchestra": [
  {"sentiment": "anger"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"ordained": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"ordeal": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"orderly": [
  {"sentiment": "positive"}
],
"ordinance": [
  {"sentiment": "trust"}
],
"ordination": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"ordnance": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"organ": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"}
],
"organic": [
  {"sentiment": "positive"}
],
"organization": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"organize": [
  {"sentiment": "positive"}
],
"organized": [
  {"sentiment": "positive"}
],
"orgasm": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"originality": [
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"ornamented": [
  {"sentiment": "positive"}
],
"ornate": [
  {"sentiment": "positive"}
],
"orphan": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"orthodoxy": [
  {"sentiment": "trust"}
],
"ostensibly": [
  {"sentiment": "negative"}
],
"oust": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"outburst": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"outcast": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"outcome": [
  {"sentiment": "positive"}
],
"outcry": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"outdo": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"outhouse": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"outlandish": [
  {"sentiment": "negative"}
],
"outlaw": [
  {"sentiment": "negative"}
],
"outpost": [
  {"sentiment": "fear"}
],
"outrage": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"outrageous": [
  {"sentiment": "surprise"}
],
"outsider": [
  {"sentiment": "fear"}
],
"outstanding": [
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"outward": [
  {"sentiment": "positive"}
],
"ovation": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"overbearing": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"overburden": [
  {"sentiment": "negative"}
],
"overcast": [
  {"sentiment": "negative"}
],
"overdo": [
  {"sentiment": "negative"}
],
"overdose": [
  {"sentiment": "negative"}
],
"overdue": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"overestimate": [
  {"sentiment": "surprise"}
],
"overestimated": [
  {"sentiment": "negative"}
],
"overflow": [
  {"sentiment": "negative"}
],
"overgrown": [
  {"sentiment": "negative"}
],
"overjoyed": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"overload": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"overpaid": [
  {"sentiment": "negative"}
],
"overpower": [
  {"sentiment": "negative"}
],
"overpowering": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"overpriced": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"oversight": [
  {"sentiment": "negative"}
],
"overt": [
  {"sentiment": "fear"}
],
"overthrow": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"overture": [
  {"sentiment": "anticipation"}
],
"overturn": [
  {"sentiment": "negative"}
],
"overwhelm": [
  {"sentiment": "negative"}
],
"overwhelmed": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"overwhelming": [
  {"sentiment": "positive"}
],
"owing": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"ownership": [
  {"sentiment": "positive"}
],
"oxidation": [
  {"sentiment": "negative"}
],
"pacific": [
  {"sentiment": "positive"}
],
"pact": [
  {"sentiment": "trust"}
],
"pad": [
  {"sentiment": "positive"}
],
"padding": [
  {"sentiment": "positive"}
],
"paddle": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"pain": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"pained": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"painful": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"painfully": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"pains": [
  {"sentiment": "negative"}
],
"palatable": [
  {"sentiment": "positive"}
],
"palliative": [
  {"sentiment": "positive"}
],
"palpable": [
  {"sentiment": "surprise"}
],
"palsy": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"panacea": [
  {"sentiment": "positive"}
],
"panache": [
  {"sentiment": "positive"}
],
"pandemic": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"pang": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"panic": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"panier": [
  {"sentiment": "positive"}
],
"paprika": [
  {"sentiment": "positive"}
],
"parachute": [
  {"sentiment": "fear"}
],
"parade": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"paragon": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"paralysis": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"paralyzed": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"paramount": [
  {"sentiment": "positive"}
],
"paranoia": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"paraphrase": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"parasite": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"pardon": [
  {"sentiment": "positive"}
],
"pare": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"parentage": [
  {"sentiment": "positive"}
],
"parietal": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"parish": [
  {"sentiment": "trust"}
],
"parliament": [
  {"sentiment": "trust"}
],
"parole": [
  {"sentiment": "anticipation"}
],
"parrot": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"parsimonious": [
  {"sentiment": "negative"}
],
"partake": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"participation": [
  {"sentiment": "positive"}
],
"parting": [
  {"sentiment": "sadness"}
],
"partisan": [
  {"sentiment": "negative"}
],
"partner": [
  {"sentiment": "positive"}
],
"partnership": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"passe": [
  {"sentiment": "negative"}
],
"passenger": [
  {"sentiment": "anticipation"}
],
"passion": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"passionate": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"passive": [
  {"sentiment": "negative"}
],
"passivity": [
  {"sentiment": "negative"}
],
"pastime": [
  {"sentiment": "positive"}
],
"pastor": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"pastry": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"pasture": [
  {"sentiment": "positive"}
],
"patch": [
  {"sentiment": "negative"}
],
"patent": [
  {"sentiment": "positive"}
],
"pathetic": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"patience": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"patient": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"patriarchal": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"patrol": [
  {"sentiment": "trust"}
],
"patron": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"patronage": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"patronizing": [
  {"sentiment": "negative"}
],
"patter": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"paucity": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"pauper": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"pavement": [
  {"sentiment": "trust"}
],
"pawn": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"pay": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"payback": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"payment": [
  {"sentiment": "negative"}
],
"peace": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"peaceable": [
  {"sentiment": "positive"}
],
"peaceful": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"peacock": [
  {"sentiment": "positive"}
],
"peck": [
  {"sentiment": "positive"}
],
"peculiarities": [
  {"sentiment": "negative"}
],
"peculiarity": [
  {"sentiment": "positive"}
],
"pedestrian": [
  {"sentiment": "negative"}
],
"pedigree": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"peerless": [
  {"sentiment": "positive"}
],
"penal": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"penalty": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"penance": [
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"penchant": [
  {"sentiment": "positive"}
],
"penetration": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"penitentiary": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"pensive": [
  {"sentiment": "sadness"}
],
"perceive": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"perceptible": [
  {"sentiment": "positive"}
],
"perchance": [
  {"sentiment": "surprise"}
],
"perdition": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"perennial": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"perfect": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"perfection": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"performer": [
  {"sentiment": "positive"}
],
"peri": [
  {"sentiment": "surprise"}
],
"peril": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"perilous": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"periodicity": [
  {"sentiment": "trust"}
],
"perish": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"perishable": [
  {"sentiment": "negative"}
],
"perished": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"perishing": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"perjury": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"perk": [
  {"sentiment": "positive"}
],
"permission": [
  {"sentiment": "positive"}
],
"pernicious": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"perpetrator": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"perpetuate": [
  {"sentiment": "anticipation"}
],
"perpetuation": [
  {"sentiment": "negative"}
],
"perpetuity": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"perplexed": [
  {"sentiment": "negative"}
],
"perplexity": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"persecute": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"persecution": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"persistence": [
  {"sentiment": "positive"}
],
"persistent": [
  {"sentiment": "positive"}
],
"personable": [
  {"sentiment": "positive"}
],
"personal": [
  {"sentiment": "trust"}
],
"perspiration": [
  {"sentiment": "disgust"}
],
"persuade": [
  {"sentiment": "trust"}
],
"pertinent": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"perturbation": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"pertussis": [
  {"sentiment": "negative"}
],
"perverse": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"perversion": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"pervert": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"perverted": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"pessimism": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"pessimist": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"pest": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"pestilence": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"pet": [
  {"sentiment": "negative"}
],
"petroleum": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"petty": [
  {"sentiment": "negative"}
],
"phalanx": [
  {"sentiment": "fear"},
  {"sentiment": "trust"}
],
"phantom": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"pharmaceutical": [
  {"sentiment": "positive"}
],
"philanthropic": [
  {"sentiment": "trust"}
],
"philanthropist": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"philanthropy": [
  {"sentiment": "positive"}
],
"philosopher": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"phlegm": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"phoenix": [
  {"sentiment": "positive"}
],
"phonetic": [
  {"sentiment": "positive"}
],
"phony": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"physician": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"physicist": [
  {"sentiment": "trust"}
],
"physics": [
  {"sentiment": "positive"}
],
"physiology": [
  {"sentiment": "positive"}
],
"physique": [
  {"sentiment": "positive"}
],
"pick": [
  {"sentiment": "positive"}
],
"picket": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"picketing": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"pickle": [
  {"sentiment": "negative"}
],
"picnic": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"picturesque": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"piety": [
  {"sentiment": "positive"}
],
"pig": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"pigeon": [
  {"sentiment": "negative"}
],
"piles": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"pill": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"pillage": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"pillow": [
  {"sentiment": "positive"}
],
"pilot": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"pimp": [
  {"sentiment": "negative"}
],
"pimple": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"pine": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"pinion": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"pinnacle": [
  {"sentiment": "positive"}
],
"pioneer": [
  {"sentiment": "positive"}
],
"pious": [
  {"sentiment": "disgust"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"pique": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"piracy": [
  {"sentiment": "negative"}
],
"pirate": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"pistol": [
  {"sentiment": "negative"}
],
"pitfall": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"pith": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"pity": [
  {"sentiment": "sadness"}
],
"pivot": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"placard": [
  {"sentiment": "surprise"}
],
"placid": [
  {"sentiment": "positive"}
],
"plagiarism": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"plague": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"plaintiff": [
  {"sentiment": "negative"}
],
"plaintive": [
  {"sentiment": "sadness"}
],
"plan": [
  {"sentiment": "anticipation"}
],
"planning": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"plated": [
  {"sentiment": "negative"}
],
"player": [
  {"sentiment": "negative"}
],
"playful": [
  {"sentiment": "anger"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"playground": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"playhouse": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"plea": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"pleasant": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"pleased": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"pleasurable": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"pledge": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"plenary": [
  {"sentiment": "positive"}
],
"plentiful": [
  {"sentiment": "positive"}
],
"plexus": [
  {"sentiment": "positive"}
],
"plight": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"plodding": [
  {"sentiment": "negative"}
],
"plum": [
  {"sentiment": "positive"}
],
"plumb": [
  {"sentiment": "positive"}
],
"plummet": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"plump": [
  {"sentiment": "anticipation"}
],
"plunder": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"plunge": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"plush": [
  {"sentiment": "positive"}
],
"ply": [
  {"sentiment": "positive"}
],
"pneumonia": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"poaching": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"pointedly": [
  {"sentiment": "positive"}
],
"pointless": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"poison": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"poisoned": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"poisoning": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"poisonous": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"poke": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"polarity": [
  {"sentiment": "surprise"}
],
"polemic": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"police": [
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"policeman": [
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"policy": [
  {"sentiment": "trust"}
],
"polio": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"polish": [
  {"sentiment": "positive"}
],
"polished": [
  {"sentiment": "positive"}
],
"polite": [
  {"sentiment": "positive"}
],
"politeness": [
  {"sentiment": "positive"}
],
"politic": [
  {"sentiment": "disgust"},
  {"sentiment": "positive"}
],
"politics": [
  {"sentiment": "anger"}
],
"poll": [
  {"sentiment": "trust"}
],
"pollute": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"pollution": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"polygamy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"pomp": [
  {"sentiment": "negative"}
],
"pompous": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"ponderous": [
  {"sentiment": "negative"}
],
"pontiff": [
  {"sentiment": "trust"}
],
"pool": [
  {"sentiment": "positive"}
],
"poorly": [
  {"sentiment": "negative"}
],
"pop": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"pope": [
  {"sentiment": "positive"}
],
"popularity": [
  {"sentiment": "positive"}
],
"popularized": [
  {"sentiment": "positive"}
],
"population": [
  {"sentiment": "positive"}
],
"porcupine": [
  {"sentiment": "negative"}
],
"porn": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"porno": [
  {"sentiment": "negative"}
],
"pornographic": [
  {"sentiment": "negative"}
],
"pornography": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"portable": [
  {"sentiment": "positive"}
],
"pose": [
  {"sentiment": "negative"}
],
"posse": [
  {"sentiment": "fear"}
],
"possess": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"possessed": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"possession": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"possibility": [
  {"sentiment": "anticipation"}
],
"posthumous": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"postponement": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"potable": [
  {"sentiment": "positive"}
],
"potency": [
  {"sentiment": "positive"}
],
"pound": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"poverty": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"pow": [
  {"sentiment": "anger"}
],
"powerful": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"powerfully": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"powerless": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"practice": [
  {"sentiment": "positive"}
],
"practiced": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"practise": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"praise": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"praised": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"praiseworthy": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"prank": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"pray": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"precarious": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"precaution": [
  {"sentiment": "positive"}
],
"precede": [
  {"sentiment": "positive"}
],
"precedence": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"precinct": [
  {"sentiment": "negative"}
],
"precious": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"precipice": [
  {"sentiment": "fear"}
],
"precise": [
  {"sentiment": "positive"}
],
"precision": [
  {"sentiment": "positive"}
],
"preclude": [
  {"sentiment": "anger"}
],
"precursor": [
  {"sentiment": "anticipation"}
],
"predatory": [
  {"sentiment": "negative"}
],
"predicament": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"predict": [
  {"sentiment": "anticipation"}
],
"prediction": [
  {"sentiment": "anticipation"}
],
"predilection": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"predispose": [
  {"sentiment": "anticipation"}
],
"predominant": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"preeminent": [
  {"sentiment": "positive"}
],
"prefer": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"pregnancy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"prejudice": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"prejudiced": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"prejudicial": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"preliminary": [
  {"sentiment": "anticipation"}
],
"premature": [
  {"sentiment": "surprise"}
],
"premeditated": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"premier": [
  {"sentiment": "positive"}
],
"premises": [
  {"sentiment": "positive"}
],
"preparation": [
  {"sentiment": "anticipation"}
],
"preparatory": [
  {"sentiment": "anticipation"}
],
"prepare": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"prepared": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"preparedness": [
  {"sentiment": "anticipation"}
],
"preponderance": [
  {"sentiment": "trust"}
],
"prerequisite": [
  {"sentiment": "anticipation"}
],
"prescient": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"presence": [
  {"sentiment": "positive"}
],
"present": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"presentable": [
  {"sentiment": "positive"}
],
"presentment": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"preservative": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"preserve": [
  {"sentiment": "positive"}
],
"president": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"pressure": [
  {"sentiment": "negative"}
],
"prestige": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"presto": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"presumption": [
  {"sentiment": "trust"}
],
"presumptuous": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"pretend": [
  {"sentiment": "negative"}
],
"pretended": [
  {"sentiment": "negative"}
],
"pretending": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"pretense": [
  {"sentiment": "negative"}
],
"pretensions": [
  {"sentiment": "negative"}
],
"pretty": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"prevail": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"prevalent": [
  {"sentiment": "trust"}
],
"prevent": [
  {"sentiment": "fear"}
],
"prevention": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"preventive": [
  {"sentiment": "negative"}
],
"prey": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"priceless": [
  {"sentiment": "positive"}
],
"prick": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"prickly": [
  {"sentiment": "negative"}
],
"pride": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"priest": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"priesthood": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"priestly": [
  {"sentiment": "positive"}
],
"primacy": [
  {"sentiment": "positive"}
],
"primary": [
  {"sentiment": "positive"}
],
"prime": [
  {"sentiment": "positive"}
],
"primer": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"prince": [
  {"sentiment": "positive"}
],
"princely": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"princess": [
  {"sentiment": "positive"}
],
"principal": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"prison": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"prisoner": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"pristine": [
  {"sentiment": "positive"}
],
"privileged": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"privy": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"probability": [
  {"sentiment": "anticipation"}
],
"probation": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"probity": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"problem": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"procedure": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"proceeding": [
  {"sentiment": "anticipation"}
],
"procession": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"procrastinate": [
  {"sentiment": "negative"}
],
"procrastination": [
  {"sentiment": "negative"}
],
"proctor": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"procure": [
  {"sentiment": "positive"}
],
"prodigal": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"prodigious": [
  {"sentiment": "positive"}
],
"prodigy": [
  {"sentiment": "positive"}
],
"producer": [
  {"sentiment": "positive"}
],
"producing": [
  {"sentiment": "positive"}
],
"production": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"productive": [
  {"sentiment": "positive"}
],
"productivity": [
  {"sentiment": "positive"}
],
"profane": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"profanity": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"profession": [
  {"sentiment": "positive"}
],
"professional": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"professor": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"professorship": [
  {"sentiment": "trust"}
],
"proficiency": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"proficient": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"profuse": [
  {"sentiment": "positive"}
],
"profusion": [
  {"sentiment": "negative"}
],
"prognosis": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"}
],
"prognostic": [
  {"sentiment": "anticipation"}
],
"progress": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"progression": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"progressive": [
  {"sentiment": "positive"}
],
"prohibited": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"prohibition": [
  {"sentiment": "negative"}
],
"projectile": [
  {"sentiment": "fear"}
],
"projectiles": [
  {"sentiment": "fear"}
],
"prolific": [
  {"sentiment": "positive"}
],
"prologue": [
  {"sentiment": "anticipation"}
],
"prolong": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"prominence": [
  {"sentiment": "positive"}
],
"prominently": [
  {"sentiment": "positive"}
],
"promiscuous": [
  {"sentiment": "negative"}
],
"promise": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"promising": [
  {"sentiment": "positive"}
],
"promotion": [
  {"sentiment": "positive"}
],
"proof": [
  {"sentiment": "trust"}
],
"prop": [
  {"sentiment": "positive"}
],
"propaganda": [
  {"sentiment": "negative"}
],
"proper": [
  {"sentiment": "positive"}
],
"prophecy": [
  {"sentiment": "anticipation"}
],
"prophet": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"prophetic": [
  {"sentiment": "anticipation"}
],
"prophylactic": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"proposition": [
  {"sentiment": "positive"}
],
"prosecute": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"prosecution": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"prospect": [
  {"sentiment": "positive"}
],
"prospectively": [
  {"sentiment": "anticipation"}
],
"prosper": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"prosperity": [
  {"sentiment": "positive"}
],
"prosperous": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"prostitute": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"prostitution": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"protect": [
  {"sentiment": "positive"}
],
"protected": [
  {"sentiment": "trust"}
],
"protecting": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"protective": [
  {"sentiment": "positive"}
],
"protector": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"protestant": [
  {"sentiment": "fear"}
],
"proud": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"prove": [
  {"sentiment": "positive"}
],
"proven": [
  {"sentiment": "trust"}
],
"proverbial": [
  {"sentiment": "positive"}
],
"provide": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"providing": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"proviso": [
  {"sentiment": "trust"}
],
"provocation": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"provoking": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"prowl": [
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"proxy": [
  {"sentiment": "trust"}
],
"prudence": [
  {"sentiment": "positive"}
],
"prudent": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"pry": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"prying": [
  {"sentiment": "negative"}
],
"psalm": [
  {"sentiment": "positive"}
],
"psychosis": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"public": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"publicist": [
  {"sentiment": "negative"}
],
"puffy": [
  {"sentiment": "negative"}
],
"puke": [
  {"sentiment": "disgust"}
],
"pull": [
  {"sentiment": "positive"}
],
"pulpit": [
  {"sentiment": "positive"}
],
"puma": [
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"punch": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"punctual": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"punctuality": [
  {"sentiment": "positive"}
],
"pungent": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"punish": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"punished": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"punishing": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"punishment": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"punitive": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"punt": [
  {"sentiment": "anticipation"}
],
"puppy": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"purely": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"purgatory": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"purge": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"purification": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"purify": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"purist": [
  {"sentiment": "positive"}
],
"purity": [
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"purr": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"pus": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"putative": [
  {"sentiment": "trust"}
],
"quack": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"quagmire": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"quail": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"quaint": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"quake": [
  {"sentiment": "fear"}
],
"qualified": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"qualifying": [
  {"sentiment": "positive"}
],
"quandary": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"quarantine": [
  {"sentiment": "fear"}
],
"quarrel": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"quash": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"quell": [
  {"sentiment": "negative"}
],
"quest": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"question": [
  {"sentiment": "positive"}
],
"questionable": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"quicken": [
  {"sentiment": "anticipation"}
],
"quickness": [
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"quicksilver": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"quiescent": [
  {"sentiment": "positive"}
],
"quiet": [
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"quinine": [
  {"sentiment": "positive"}
],
"quit": [
  {"sentiment": "negative"}
],
"quiver": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"quivering": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"quiz": [
  {"sentiment": "negative"}
],
"quote": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"rabble": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"rabid": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"rabies": [
  {"sentiment": "negative"}
],
"rack": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"racket": [
  {"sentiment": "negative"}
],
"radar": [
  {"sentiment": "trust"}
],
"radiance": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"radiant": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"radiate": [
  {"sentiment": "positive"}
],
"radiation": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"radio": [
  {"sentiment": "positive"}
],
"radioactive": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"radon": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"raffle": [
  {"sentiment": "anticipation"},
  {"sentiment": "surprise"}
],
"rage": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"raging": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"rags": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"raid": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"rail": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"rainy": [
  {"sentiment": "sadness"}
],
"ram": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"rambling": [
  {"sentiment": "negative"}
],
"rampage": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"rancid": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"randomly": [
  {"sentiment": "surprise"}
],
"ranger": [
  {"sentiment": "trust"}
],
"ransom": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"rape": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"rapid": [
  {"sentiment": "surprise"}
],
"rapping": [
  {"sentiment": "anger"}
],
"rapt": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"raptors": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"rapture": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"rarity": [
  {"sentiment": "surprise"}
],
"rascal": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"rash": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"rat": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"ratify": [
  {"sentiment": "trust"}
],
"rating": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"rational": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"rattlesnake": [
  {"sentiment": "fear"}
],
"raucous": [
  {"sentiment": "negative"}
],
"rave": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"ravenous": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ravine": [
  {"sentiment": "fear"}
],
"raving": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"razor": [
  {"sentiment": "fear"}
],
"react": [
  {"sentiment": "anger"},
  {"sentiment": "fear"}
],
"reactionary": [
  {"sentiment": "negative"}
],
"reader": [
  {"sentiment": "positive"}
],
"readily": [
  {"sentiment": "positive"}
],
"readiness": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"reading": [
  {"sentiment": "positive"}
],
"ready": [
  {"sentiment": "anticipation"}
],
"reaffirm": [
  {"sentiment": "positive"}
],
"real": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"reappear": [
  {"sentiment": "surprise"}
],
"rear": [
  {"sentiment": "negative"}
],
"reason": [
  {"sentiment": "positive"}
],
"reassurance": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"reassure": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"rebate": [
  {"sentiment": "positive"}
],
"rebel": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"rebellion": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"}
],
"rebuke": [
  {"sentiment": "negative"}
],
"rebut": [
  {"sentiment": "negative"}
],
"recalcitrant": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"recast": [
  {"sentiment": "positive"}
],
"received": [
  {"sentiment": "positive"}
],
"receiving": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"recesses": [
  {"sentiment": "fear"}
],
"recession": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"recherche": [
  {"sentiment": "positive"}
],
"recidivism": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"recipient": [
  {"sentiment": "anticipation"}
],
"reciprocate": [
  {"sentiment": "positive"}
],
"reckless": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"recklessness": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"reclamation": [
  {"sentiment": "positive"}
],
"recline": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"recognizable": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"recombination": [
  {"sentiment": "anticipation"}
],
"recommend": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"reconciliation": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"reconsideration": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"reconstruct": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"reconstruction": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"recorder": [
  {"sentiment": "positive"}
],
"recoup": [
  {"sentiment": "positive"}
],
"recovery": [
  {"sentiment": "positive"}
],
"recreation": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"recreational": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"recruits": [
  {"sentiment": "trust"}
],
"rectify": [
  {"sentiment": "positive"}
],
"recurrent": [
  {"sentiment": "anticipation"}
],
"redemption": [
  {"sentiment": "positive"}
],
"redress": [
  {"sentiment": "positive"}
],
"redundant": [
  {"sentiment": "negative"}
],
"referee": [
  {"sentiment": "trust"}
],
"refine": [
  {"sentiment": "positive"}
],
"refinement": [
  {"sentiment": "positive"}
],
"reflex": [
  {"sentiment": "surprise"}
],
"reflux": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"reform": [
  {"sentiment": "positive"}
],
"reformation": [
  {"sentiment": "positive"}
],
"reformer": [
  {"sentiment": "positive"}
],
"refractory": [
  {"sentiment": "negative"}
],
"refreshing": [
  {"sentiment": "positive"}
],
"refugee": [
  {"sentiment": "sadness"}
],
"refurbish": [
  {"sentiment": "positive"}
],
"refusal": [
  {"sentiment": "negative"}
],
"refuse": [
  {"sentiment": "negative"}
],
"refused": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"refutation": [
  {"sentiment": "fear"}
],
"regal": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"regatta": [
  {"sentiment": "anticipation"}
],
"regent": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"regiment": [
  {"sentiment": "fear"}
],
"registry": [
  {"sentiment": "trust"}
],
"regress": [
  {"sentiment": "negative"}
],
"regression": [
  {"sentiment": "negative"}
],
"regressive": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"regret": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"regrettable": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"regretted": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"regretting": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"regularity": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"regulate": [
  {"sentiment": "positive"}
],
"regulatory": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"regurgitation": [
  {"sentiment": "disgust"}
],
"rehabilitate": [
  {"sentiment": "positive"}
],
"rehabilitation": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"reimburse": [
  {"sentiment": "positive"}
],
"reimbursement": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"rein": [
  {"sentiment": "negative"}
],
"reinforcement": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"reinforcements": [
  {"sentiment": "trust"}
],
"reinstate": [
  {"sentiment": "positive"}
],
"reject": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"rejected": [
  {"sentiment": "negative"}
],
"rejection": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"rejects": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"rejoice": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"rejoicing": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"rejuvenate": [
  {"sentiment": "positive"}
],
"rejuvenated": [
  {"sentiment": "positive"}
],
"rekindle": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"relapse": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"related": [
  {"sentiment": "trust"}
],
"relative": [
  {"sentiment": "trust"}
],
"relaxation": [
  {"sentiment": "positive"}
],
"relegation": [
  {"sentiment": "negative"}
],
"relevant": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"reliability": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"reliable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"reliance": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"relics": [
  {"sentiment": "sadness"}
],
"relief": [
  {"sentiment": "positive"}
],
"relieving": [
  {"sentiment": "positive"}
],
"religion": [
  {"sentiment": "trust"}
],
"relinquish": [
  {"sentiment": "negative"}
],
"reluctant": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"remains": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"remake": [
  {"sentiment": "positive"}
],
"remand": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"remarkable": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"remarkably": [
  {"sentiment": "positive"}
],
"remedial": [
  {"sentiment": "negative"}
],
"remedy": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"remiss": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"remission": [
  {"sentiment": "positive"}
],
"remodel": [
  {"sentiment": "positive"}
],
"remorse": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"removal": [
  {"sentiment": "negative"}
],
"remove": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"renaissance": [
  {"sentiment": "positive"}
],
"rencontre": [
  {"sentiment": "negative"}
],
"rend": [
  {"sentiment": "negative"}
],
"render": [
  {"sentiment": "positive"}
],
"renegade": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"renewal": [
  {"sentiment": "positive"}
],
"renounce": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"renovate": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"renovation": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"renown": [
  {"sentiment": "positive"}
],
"renowned": [
  {"sentiment": "positive"}
],
"renunciation": [
  {"sentiment": "negative"}
],
"reorganize": [
  {"sentiment": "positive"}
],
"reparation": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"repay": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"repellant": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"repellent": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"repelling": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"repent": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"replenish": [
  {"sentiment": "positive"}
],
"replete": [
  {"sentiment": "positive"}
],
"reporter": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"repose": [
  {"sentiment": "positive"}
],
"represented": [
  {"sentiment": "positive"}
],
"representing": [
  {"sentiment": "anticipation"}
],
"repress": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"repression": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"reprimand": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"reprint": [
  {"sentiment": "negative"}
],
"reprisal": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"reproach": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"reproductive": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"republic": [
  {"sentiment": "negative"}
],
"repudiation": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"repulsion": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"reputable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"requiem": [
  {"sentiment": "sadness"}
],
"rescind": [
  {"sentiment": "negative"}
],
"rescission": [
  {"sentiment": "negative"}
],
"rescue": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"resection": [
  {"sentiment": "fear"}
],
"resent": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"resentful": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"resentment": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"reserve": [
  {"sentiment": "positive"}
],
"resident": [
  {"sentiment": "positive"}
],
"resign": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"resignation": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"resigned": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"resilient": [
  {"sentiment": "positive"}
],
"resist": [
  {"sentiment": "negative"}
],
"resistance": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"resistant": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"resisting": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"resistive": [
  {"sentiment": "positive"}
],
"resolutely": [
  {"sentiment": "positive"}
],
"resources": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"respect": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"respectability": [
  {"sentiment": "positive"}
],
"respectable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"respectful": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"respecting": [
  {"sentiment": "positive"}
],
"respects": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"respite": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"resplendent": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"responsible": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"responsive": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"rest": [
  {"sentiment": "positive"}
],
"restful": [
  {"sentiment": "positive"}
],
"restitution": [
  {"sentiment": "anger"},
  {"sentiment": "positive"}
],
"restlessness": [
  {"sentiment": "anticipation"}
],
"restorative": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"restoring": [
  {"sentiment": "positive"}
],
"restrain": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"restrained": [
  {"sentiment": "fear"}
],
"restraint": [
  {"sentiment": "positive"}
],
"restrict": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"restriction": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"restrictive": [
  {"sentiment": "negative"}
],
"result": [
  {"sentiment": "anticipation"}
],
"resultant": [
  {"sentiment": "anticipation"}
],
"resumption": [
  {"sentiment": "positive"}
],
"retain": [
  {"sentiment": "trust"}
],
"retaliate": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"retaliation": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"retaliatory": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"retard": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"retardation": [
  {"sentiment": "negative"}
],
"retention": [
  {"sentiment": "positive"}
],
"reticent": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"retirement": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"retort": [
  {"sentiment": "negative"}
],
"retract": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"retraction": [
  {"sentiment": "negative"}
],
"retrenchment": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"retribution": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"retrograde": [
  {"sentiment": "negative"}
],
"reunion": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"revel": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"revels": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"revenge": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"revere": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"reverence": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"reverend": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"reverie": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"reversal": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"revise": [
  {"sentiment": "positive"}
],
"revival": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"revive": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"revocation": [
  {"sentiment": "negative"}
],
"revoke": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"revolt": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"revolting": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"revolution": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"revolutionary": [
  {"sentiment": "positive"}
],
"revolver": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"revulsion": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"reward": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"rheumatism": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"rhythm": [
  {"sentiment": "positive"}
],
"rhythmical": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"ribbon": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"richness": [
  {"sentiment": "positive"}
],
"rickety": [
  {"sentiment": "negative"}
],
"riddle": [
  {"sentiment": "surprise"}
],
"riddled": [
  {"sentiment": "negative"}
],
"rider": [
  {"sentiment": "positive"}
],
"ridicule": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ridiculous": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"rife": [
  {"sentiment": "negative"}
],
"rifle": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"rift": [
  {"sentiment": "negative"}
],
"righteous": [
  {"sentiment": "positive"}
],
"rightful": [
  {"sentiment": "positive"}
],
"rightly": [
  {"sentiment": "positive"}
],
"rigid": [
  {"sentiment": "negative"}
],
"rigidity": [
  {"sentiment": "negative"}
],
"rigor": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"rigorous": [
  {"sentiment": "negative"}
],
"ringer": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"riot": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"riotous": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"ripe": [
  {"sentiment": "positive"}
],
"ripen": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"rising": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"risk": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"risky": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"rivalry": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"riveting": [
  {"sentiment": "positive"}
],
"roadster": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"rob": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"robber": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"robbery": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"robust": [
  {"sentiment": "positive"}
],
"rock": [
  {"sentiment": "positive"}
],
"rocket": [
  {"sentiment": "anger"}
],
"rod": [
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"rogue": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"rollicking": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"romance": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"romantic": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"romanticism": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"romp": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"rook": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"rooted": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"rosy": [
  {"sentiment": "positive"}
],
"rot": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"rota": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"rotting": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"roughness": [
  {"sentiment": "negative"}
],
"roulette": [
  {"sentiment": "anticipation"}
],
"rout": [
  {"sentiment": "negative"}
],
"routine": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"row": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"rowdy": [
  {"sentiment": "negative"}
],
"royalty": [
  {"sentiment": "positive"}
],
"rubbish": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"rubble": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"rubric": [
  {"sentiment": "positive"}
],
"rue": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ruffle": [
  {"sentiment": "negative"}
],
"rugged": [
  {"sentiment": "negative"}
],
"ruin": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ruined": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ruinous": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"rule": [
  {"sentiment": "fear"},
  {"sentiment": "trust"}
],
"rumor": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"runaway": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"rupture": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"ruse": [
  {"sentiment": "negative"}
],
"rust": [
  {"sentiment": "negative"}
],
"rusty": [
  {"sentiment": "negative"}
],
"ruth": [
  {"sentiment": "positive"}
],
"ruthless": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"saber": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"sabotage": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"sacrifices": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sadly": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sadness": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"safe": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"safeguard": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"safekeeping": [
  {"sentiment": "trust"}
],
"sag": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"sage": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"saint": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"saintly": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"salary": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"salient": [
  {"sentiment": "positive"}
],
"saliva": [
  {"sentiment": "anticipation"}
],
"sally": [
  {"sentiment": "surprise"}
],
"saloon": [
  {"sentiment": "anger"}
],
"salutary": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"salute": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"salvation": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"salve": [
  {"sentiment": "positive"}
],
"samurai": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"sanctification": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"sanctify": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"sanctuary": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"sanguine": [
  {"sentiment": "positive"}
],
"sanitary": [
  {"sentiment": "positive"}
],
"sap": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sappy": [
  {"sentiment": "trust"}
],
"sarcasm": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sarcoma": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sardonic": [
  {"sentiment": "negative"}
],
"satanic": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"satin": [
  {"sentiment": "positive"}
],
"satisfactorily": [
  {"sentiment": "positive"}
],
"satisfied": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"saturated": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"savage": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"savagery": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"save": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"savings": [
  {"sentiment": "positive"}
],
"savor": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"savory": [
  {"sentiment": "positive"}
],
"savvy": [
  {"sentiment": "positive"}
],
"scab": [
  {"sentiment": "negative"}
],
"scaffold": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"scalpel": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"scaly": [
  {"sentiment": "negative"}
],
"scandal": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"scandalous": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"scanty": [
  {"sentiment": "negative"}
],
"scapegoat": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"scar": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"scarce": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"scarcely": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"scarcity": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"scare": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"scarecrow": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"scavenger": [
  {"sentiment": "negative"}
],
"sceptical": [
  {"sentiment": "trust"}
],
"scheme": [
  {"sentiment": "negative"}
],
"schism": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"schizophrenia": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"scholar": [
  {"sentiment": "positive"}
],
"scholarship": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"school": [
  {"sentiment": "trust"}
],
"sciatica": [
  {"sentiment": "negative"}
],
"scientific": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"scientist": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"scintilla": [
  {"sentiment": "positive"}
],
"scoff": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"scold": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"scolding": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"scorching": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"score": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"scorn": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"scorpion": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"scotch": [
  {"sentiment": "negative"}
],
"scoundrel": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"scourge": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"scrambling": [
  {"sentiment": "negative"}
],
"scrapie": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"scream": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"screaming": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"screech": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"screwed": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"scribe": [
  {"sentiment": "positive"}
],
"scrimmage": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"script": [
  {"sentiment": "positive"}
],
"scripture": [
  {"sentiment": "trust"}
],
"scrub": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"scrumptious": [
  {"sentiment": "positive"}
],
"scrutinize": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"scrutiny": [
  {"sentiment": "negative"}
],
"sculpture": [
  {"sentiment": "positive"}
],
"scum": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"sea": [
  {"sentiment": "positive"}
],
"seal": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"seals": [
  {"sentiment": "trust"}
],
"sear": [
  {"sentiment": "negative"}
],
"seasoned": [
  {"sentiment": "positive"}
],
"secession": [
  {"sentiment": "negative"}
],
"secluded": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"seclusion": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"secondhand": [
  {"sentiment": "negative"}
],
"secrecy": [
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"secret": [
  {"sentiment": "trust"}
],
"secretariat": [
  {"sentiment": "positive"}
],
"secrete": [
  {"sentiment": "disgust"}
],
"secretion": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"secretive": [
  {"sentiment": "negative"}
],
"sectarian": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"secular": [
  {"sentiment": "anticipation"}
],
"securities": [
  {"sentiment": "trust"}
],
"sedition": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"seduce": [
  {"sentiment": "negative"}
],
"seduction": [
  {"sentiment": "negative"}
],
"seductive": [
  {"sentiment": "anticipation"}
],
"seek": [
  {"sentiment": "anticipation"}
],
"segregate": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"segregated": [
  {"sentiment": "negative"}
],
"seize": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"seizure": [
  {"sentiment": "fear"}
],
"selfish": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"selfishness": [
  {"sentiment": "negative"}
],
"senate": [
  {"sentiment": "trust"}
],
"senile": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"seniority": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"sensational": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"sense": [
  {"sentiment": "positive"}
],
"senseless": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"sensibility": [
  {"sentiment": "positive"}
],
"sensibly": [
  {"sentiment": "positive"}
],
"sensual": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"sensuality": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"sensuous": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"sentence": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sentimental": [
  {"sentiment": "positive"}
],
"sentimentality": [
  {"sentiment": "positive"}
],
"sentinel": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"sentry": [
  {"sentiment": "trust"}
],
"separatist": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"sepsis": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"septic": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"sequel": [
  {"sentiment": "anticipation"}
],
"sequestration": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"serene": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"serenity": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"serial": [
  {"sentiment": "anticipation"}
],
"series": [
  {"sentiment": "trust"}
],
"seriousness": [
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"sermon": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"serpent": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"serum": [
  {"sentiment": "positive"}
],
"servant": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"serve": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"servile": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"servitude": [
  {"sentiment": "negative"}
],
"setback": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"settlor": [
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"sever": [
  {"sentiment": "negative"}
],
"severance": [
  {"sentiment": "sadness"}
],
"sewage": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"sewer": [
  {"sentiment": "disgust"}
],
"sewerage": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"sex": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"shabby": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"shack": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"shackle": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"shady": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"shaking": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"shaky": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"sham": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"shambles": [
  {"sentiment": "negative"}
],
"shame": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"shameful": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"shameless": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"shanghai": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"shank": [
  {"sentiment": "fear"}
],
"shape": [
  {"sentiment": "positive"}
],
"shapely": [
  {"sentiment": "positive"}
],
"share": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"shark": [
  {"sentiment": "negative"}
],
"sharpen": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"}
],
"shatter": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"shattered": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"shed": [
  {"sentiment": "negative"}
],
"shell": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"shelter": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"shelved": [
  {"sentiment": "negative"}
],
"shepherd": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"sheriff": [
  {"sentiment": "trust"}
],
"shine": [
  {"sentiment": "positive"}
],
"shining": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"ship": [
  {"sentiment": "anticipation"}
],
"shipwreck": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"shit": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"shiver": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"shock": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"shockingly": [
  {"sentiment": "surprise"}
],
"shoddy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"shoot": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"shooter": [
  {"sentiment": "fear"}
],
"shooting": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"shopkeeper": [
  {"sentiment": "trust"}
],
"shoplifting": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"shopping": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"shortage": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"shortcoming": [
  {"sentiment": "negative"}
],
"shortly": [
  {"sentiment": "anticipation"}
],
"shot": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"shoulder": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"shout": [
  {"sentiment": "anger"},
  {"sentiment": "surprise"}
],
"shove": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"show": [
  {"sentiment": "trust"}
],
"showy": [
  {"sentiment": "negative"}
],
"shrapnel": [
  {"sentiment": "fear"}
],
"shrewd": [
  {"sentiment": "positive"}
],
"shriek": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"shrill": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"shrink": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"shroud": [
  {"sentiment": "sadness"}
],
"shrunk": [
  {"sentiment": "negative"}
],
"shudder": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"shun": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sib": [
  {"sentiment": "trust"}
],
"sick": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sickening": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sickly": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sickness": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"signature": [
  {"sentiment": "trust"}
],
"signify": [
  {"sentiment": "anticipation"}
],
"silk": [
  {"sentiment": "positive"}
],
"silly": [
  {"sentiment": "joy"},
  {"sentiment": "negative"}
],
"simmer": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"}
],
"simmering": [
  {"sentiment": "anticipation"}
],
"simplify": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"sin": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sincere": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"sincerity": [
  {"sentiment": "positive"}
],
"sinful": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sing": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"singly": [
  {"sentiment": "positive"}
],
"singularly": [
  {"sentiment": "surprise"}
],
"sinister": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"sinner": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sinning": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"sir": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"siren": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"sissy": [
  {"sentiment": "negative"}
],
"sisterhood": [
  {"sentiment": "anger"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"sizzle": [
  {"sentiment": "anger"}
],
"skeptical": [
  {"sentiment": "negative"}
],
"sketchy": [
  {"sentiment": "negative"}
],
"skewed": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"skid": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"skilled": [
  {"sentiment": "positive"}
],
"skillful": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"skip": [
  {"sentiment": "negative"}
],
"skirmish": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"sky": [
  {"sentiment": "positive"}
],
"slack": [
  {"sentiment": "negative"}
],
"slag": [
  {"sentiment": "negative"}
],
"slam": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"slander": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"slanderous": [
  {"sentiment": "negative"}
],
"slap": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"slash": [
  {"sentiment": "anger"}
],
"slate": [
  {"sentiment": "positive"}
],
"slaughter": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"slaughterhouse": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"slaughtering": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"slave": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"slavery": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"slay": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"slayer": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"sleek": [
  {"sentiment": "positive"}
],
"sleet": [
  {"sentiment": "negative"}
],
"slender": [
  {"sentiment": "positive"}
],
"slim": [
  {"sentiment": "positive"}
],
"slime": [
  {"sentiment": "disgust"}
],
"slimy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"slink": [
  {"sentiment": "negative"}
],
"slip": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"slop": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"sloppy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"sloth": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"slouch": [
  {"sentiment": "negative"}
],
"slough": [
  {"sentiment": "negative"}
],
"slowness": [
  {"sentiment": "negative"}
],
"sludge": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"slug": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"sluggish": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"slum": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"slump": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"slur": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"slush": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"slut": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"sly": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"smack": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"small": [
  {"sentiment": "negative"}
],
"smash": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"smashed": [
  {"sentiment": "negative"}
],
"smattering": [
  {"sentiment": "negative"}
],
"smell": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"smelling": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"smile": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"smiling": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"smirk": [
  {"sentiment": "negative"}
],
"smite": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"smith": [
  {"sentiment": "trust"}
],
"smitten": [
  {"sentiment": "positive"}
],
"smoker": [
  {"sentiment": "negative"}
],
"smoothness": [
  {"sentiment": "positive"}
],
"smother": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"smudge": [
  {"sentiment": "negative"}
],
"smug": [
  {"sentiment": "negative"}
],
"smuggle": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"smuggler": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"smuggling": [
  {"sentiment": "negative"}
],
"smut": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"snag": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"snags": [
  {"sentiment": "negative"}
],
"snake": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"snare": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"snarl": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"snarling": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"sneak": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"sneaking": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"sneer": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"sneeze": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"snicker": [
  {"sentiment": "positive"}
],
"snide": [
  {"sentiment": "negative"}
],
"snob": [
  {"sentiment": "negative"}
],
"snort": [
  {"sentiment": "sadness"}
],
"soak": [
  {"sentiment": "negative"}
],
"sob": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sobriety": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"sociable": [
  {"sentiment": "positive"}
],
"socialism": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"}
],
"socialist": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"soil": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"soiled": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"solace": [
  {"sentiment": "positive"}
],
"soldier": [
  {"sentiment": "anger"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"solid": [
  {"sentiment": "positive"}
],
"solidarity": [
  {"sentiment": "trust"}
],
"solidity": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"solution": [
  {"sentiment": "positive"}
],
"solvency": [
  {"sentiment": "positive"}
],
"somatic": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"somber": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sonar": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"sonata": [
  {"sentiment": "positive"}
],
"sonnet": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"sonorous": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"soot": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"soothe": [
  {"sentiment": "positive"}
],
"soothing": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"sorcery": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"sordid": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sore": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sorely": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"soreness": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sorrow": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sorrowful": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sorter": [
  {"sentiment": "positive"}
],
"sortie": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"soulless": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"soulmate": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"soundness": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"soup": [
  {"sentiment": "positive"}
],
"sour": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"sovereign": [
  {"sentiment": "trust"}
],
"spa": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"spacious": [
  {"sentiment": "positive"}
],
"spaniel": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"spank": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"spanking": [
  {"sentiment": "anger"}
],
"sparkle": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"spasm": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"spat": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"spear": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"special": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"specialist": [
  {"sentiment": "trust"}
],
"specialize": [
  {"sentiment": "trust"}
],
"specie": [
  {"sentiment": "positive"}
],
"speck": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"spectacle": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"spectacular": [
  {"sentiment": "anticipation"},
  {"sentiment": "surprise"}
],
"specter": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"spectral": [
  {"sentiment": "negative"}
],
"speculation": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"speculative": [
  {"sentiment": "anticipation"}
],
"speech": [
  {"sentiment": "positive"}
],
"speedy": [
  {"sentiment": "positive"}
],
"spelling": [
  {"sentiment": "positive"}
],
"spent": [
  {"sentiment": "negative"}
],
"spew": [
  {"sentiment": "disgust"}
],
"spice": [
  {"sentiment": "positive"}
],
"spider": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"}
],
"spike": [
  {"sentiment": "fear"}
],
"spine": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"spinster": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"spirit": [
  {"sentiment": "positive"}
],
"spirits": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"spit": [
  {"sentiment": "disgust"}
],
"spite": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"spiteful": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"splash": [
  {"sentiment": "surprise"}
],
"splendid": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"splendor": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"splinter": [
  {"sentiment": "negative"}
],
"split": [
  {"sentiment": "negative"}
],
"splitting": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"spoil": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"spoiler": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"spoke": [
  {"sentiment": "negative"}
],
"spokesman": [
  {"sentiment": "trust"}
],
"sponge": [
  {"sentiment": "negative"}
],
"sponsor": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"spook": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"spotless": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"spouse": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"sprain": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"spree": [
  {"sentiment": "negative"}
],
"sprite": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"spruce": [
  {"sentiment": "positive"}
],
"spur": [
  {"sentiment": "fear"}
],
"spurious": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"squall": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"squatter": [
  {"sentiment": "negative"}
],
"squeamish": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"squelch": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"squirm": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"stab": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"stable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"staccato": [
  {"sentiment": "positive"}
],
"stagger": [
  {"sentiment": "surprise"}
],
"staggering": [
  {"sentiment": "negative"}
],
"stagnant": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"stain": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"stainless": [
  {"sentiment": "positive"}
],
"stale": [
  {"sentiment": "negative"}
],
"stalemate": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"}
],
"stalk": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"stall": [
  {"sentiment": "disgust"}
],
"stallion": [
  {"sentiment": "positive"}
],
"stalwart": [
  {"sentiment": "positive"}
],
"stamina": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"standing": [
  {"sentiment": "positive"}
],
"standoff": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"standstill": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"star": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"staring": [
  {"sentiment": "negative"}
],
"stark": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"starlight": [
  {"sentiment": "positive"}
],
"starry": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"start": [
  {"sentiment": "anticipation"}
],
"startle": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"startling": [
  {"sentiment": "surprise"}
],
"starvation": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"starved": [
  {"sentiment": "negative"}
],
"starving": [
  {"sentiment": "negative"}
],
"stately": [
  {"sentiment": "positive"}
],
"statement": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"stationary": [
  {"sentiment": "negative"}
],
"statistical": [
  {"sentiment": "trust"}
],
"statue": [
  {"sentiment": "positive"}
],
"status": [
  {"sentiment": "positive"}
],
"staunch": [
  {"sentiment": "positive"}
],
"stave": [
  {"sentiment": "negative"}
],
"steadfast": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"steady": [
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"steal": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"stealing": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"stealth": [
  {"sentiment": "surprise"}
],
"stealthily": [
  {"sentiment": "surprise"}
],
"stealthy": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"stellar": [
  {"sentiment": "positive"}
],
"stereotype": [
  {"sentiment": "negative"}
],
"stereotyped": [
  {"sentiment": "negative"}
],
"sterile": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sterility": [
  {"sentiment": "negative"}
],
"sterling": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"stern": [
  {"sentiment": "negative"}
],
"steward": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"sticky": [
  {"sentiment": "disgust"}
],
"stiff": [
  {"sentiment": "negative"}
],
"stiffness": [
  {"sentiment": "negative"}
],
"stifle": [
  {"sentiment": "negative"}
],
"stifled": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"stigma": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"stillborn": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"stillness": [
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"sting": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"stinging": [
  {"sentiment": "negative"}
],
"stingy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"stink": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"stinking": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"stint": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"stocks": [
  {"sentiment": "negative"}
],
"stolen": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"stomach": [
  {"sentiment": "disgust"}
],
"stone": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"stoned": [
  {"sentiment": "negative"}
],
"stools": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"stoppage": [
  {"sentiment": "negative"}
],
"store": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"storm": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"storming": [
  {"sentiment": "anger"}
],
"stormy": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"straightforward": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"strained": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"straits": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"stranded": [
  {"sentiment": "negative"}
],
"stranger": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"strangle": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"strategic": [
  {"sentiment": "positive"}
],
"strategist": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"stray": [
  {"sentiment": "negative"}
],
"strength": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"strengthen": [
  {"sentiment": "positive"}
],
"strengthening": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"stress": [
  {"sentiment": "negative"}
],
"stretcher": [
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"stricken": [
  {"sentiment": "sadness"}
],
"strife": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"strike": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"striking": [
  {"sentiment": "positive"}
],
"strikingly": [
  {"sentiment": "positive"}
],
"strip": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"stripe": [
  {"sentiment": "negative"}
],
"stripped": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"strive": [
  {"sentiment": "anticipation"}
],
"stroke": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"strongly": [
  {"sentiment": "positive"}
],
"structural": [
  {"sentiment": "trust"}
],
"structure": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"struggle": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"strut": [
  {"sentiment": "negative"}
],
"stud": [
  {"sentiment": "positive"}
],
"study": [
  {"sentiment": "positive"}
],
"stuffy": [
  {"sentiment": "negative"}
],
"stumble": [
  {"sentiment": "negative"}
],
"stunned": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"stunted": [
  {"sentiment": "negative"}
],
"stupid": [
  {"sentiment": "negative"}
],
"stupidity": [
  {"sentiment": "negative"}
],
"stupor": [
  {"sentiment": "negative"}
],
"sturdy": [
  {"sentiment": "positive"}
],
"sty": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"subdue": [
  {"sentiment": "negative"}
],
"subito": [
  {"sentiment": "surprise"}
],
"subject": [
  {"sentiment": "negative"}
],
"subjected": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"subjection": [
  {"sentiment": "negative"}
],
"subjugation": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sublimation": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"submit": [
  {"sentiment": "anticipation"}
],
"subordinate": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"subpoena": [
  {"sentiment": "negative"}
],
"subscribe": [
  {"sentiment": "anticipation"}
],
"subsidence": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"subsidy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"subsist": [
  {"sentiment": "negative"}
],
"substance": [
  {"sentiment": "positive"}
],
"substantiate": [
  {"sentiment": "trust"}
],
"substantive": [
  {"sentiment": "positive"}
],
"subtract": [
  {"sentiment": "negative"}
],
"subversion": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"subversive": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"subvert": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"succeed": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"succeeding": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"success": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"successful": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"succinct": [
  {"sentiment": "positive"}
],
"succulent": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"succumb": [
  {"sentiment": "negative"}
],
"suck": [
  {"sentiment": "negative"}
],
"sucker": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"sudden": [
  {"sentiment": "surprise"}
],
"suddenly": [
  {"sentiment": "surprise"}
],
"sue": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"suffer": [
  {"sentiment": "negative"}
],
"sufferer": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"suffering": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sufficiency": [
  {"sentiment": "positive"}
],
"suffocating": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"suffocation": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"sugar": [
  {"sentiment": "positive"}
],
"suggest": [
  {"sentiment": "trust"}
],
"suicidal": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"suicide": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"suitable": [
  {"sentiment": "positive"}
],
"sullen": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sultan": [
  {"sentiment": "fear"}
],
"sultry": [
  {"sentiment": "positive"}
],
"summons": [
  {"sentiment": "negative"}
],
"sump": [
  {"sentiment": "disgust"}
],
"sun": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"sundial": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"sunk": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"sunny": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"sunset": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"sunshine": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"superb": [
  {"sentiment": "positive"}
],
"superficial": [
  {"sentiment": "negative"}
],
"superfluous": [
  {"sentiment": "negative"}
],
"superhuman": [
  {"sentiment": "positive"}
],
"superior": [
  {"sentiment": "positive"}
],
"superiority": [
  {"sentiment": "positive"}
],
"superman": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"superstar": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"superstition": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"superstitious": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"supplication": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"supplies": [
  {"sentiment": "positive"}
],
"supply": [
  {"sentiment": "positive"}
],
"supported": [
  {"sentiment": "positive"}
],
"supporter": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"supporting": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"suppress": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"suppression": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"supremacy": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"supreme": [
  {"sentiment": "positive"}
],
"supremely": [
  {"sentiment": "positive"}
],
"surcharge": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"surety": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"surge": [
  {"sentiment": "surprise"}
],
"surgery": [
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"surly": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"surmise": [
  {"sentiment": "positive"}
],
"surpassing": [
  {"sentiment": "positive"}
],
"surprise": [
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"surprised": [
  {"sentiment": "surprise"}
],
"surprising": [
  {"sentiment": "surprise"}
],
"surprisingly": [
  {"sentiment": "anticipation"},
  {"sentiment": "surprise"}
],
"surrender": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"surrendering": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"surrogate": [
  {"sentiment": "trust"}
],
"surround": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"surveillance": [
  {"sentiment": "fear"}
],
"surveying": [
  {"sentiment": "positive"}
],
"survive": [
  {"sentiment": "positive"}
],
"susceptible": [
  {"sentiment": "negative"}
],
"suspect": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"suspense": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"suspension": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"suspicion": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"suspicious": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"swab": [
  {"sentiment": "negative"}
],
"swamp": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"swampy": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"swarm": [
  {"sentiment": "disgust"}
],
"swastika": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"swear": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"sweat": [
  {"sentiment": "fear"}
],
"sweet": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"sweetheart": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"sweetie": [
  {"sentiment": "positive"}
],
"sweetness": [
  {"sentiment": "positive"}
],
"sweets": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"swelling": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"swerve": [
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"swift": [
  {"sentiment": "positive"}
],
"swig": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"swim": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"swine": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"swollen": [
  {"sentiment": "negative"}
],
"symbolic": [
  {"sentiment": "positive"}
],
"symmetrical": [
  {"sentiment": "positive"}
],
"symmetry": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"sympathetic": [
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"sympathize": [
  {"sentiment": "sadness"}
],
"sympathy": [
  {"sentiment": "positive"},
  {"sentiment": "sadness"}
],
"symphony": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"symptom": [
  {"sentiment": "negative"}
],
"synchronize": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"syncope": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"synergistic": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"synod": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"synonymous": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"syringe": [
  {"sentiment": "fear"}
],
"system": [
  {"sentiment": "trust"}
],
"taboo": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"tabulate": [
  {"sentiment": "anticipation"}
],
"tackle": [
  {"sentiment": "anger"},
  {"sentiment": "surprise"}
],
"tact": [
  {"sentiment": "positive"}
],
"tactics": [
  {"sentiment": "fear"},
  {"sentiment": "trust"}
],
"taint": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"tale": [
  {"sentiment": "positive"}
],
"talent": [
  {"sentiment": "positive"}
],
"talisman": [
  {"sentiment": "positive"}
],
"talk": [
  {"sentiment": "positive"}
],
"talons": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"tandem": [
  {"sentiment": "trust"}
],
"tangled": [
  {"sentiment": "negative"}
],
"tanned": [
  {"sentiment": "positive"}
],
"tantalizing": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"tantamount": [
  {"sentiment": "trust"}
],
"tardiness": [
  {"sentiment": "negative"}
],
"tardy": [
  {"sentiment": "negative"}
],
"tariff": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"tarnish": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"tarry": [
  {"sentiment": "negative"}
],
"task": [
  {"sentiment": "positive"}
],
"tasteful": [
  {"sentiment": "positive"}
],
"tasteless": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"tasty": [
  {"sentiment": "positive"}
],
"taught": [
  {"sentiment": "trust"}
],
"taunt": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"tawny": [
  {"sentiment": "disgust"}
],
"tax": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"teach": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"teacher": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"team": [
  {"sentiment": "trust"}
],
"tearful": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"tease": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"teasing": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"technology": [
  {"sentiment": "positive"}
],
"tedious": [
  {"sentiment": "negative"}
],
"tedium": [
  {"sentiment": "negative"}
],
"teeming": [
  {"sentiment": "disgust"}
],
"teens": [
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"temperance": [
  {"sentiment": "positive"}
],
"temperate": [
  {"sentiment": "trust"}
],
"tempered": [
  {"sentiment": "positive"}
],
"tempest": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"temptation": [
  {"sentiment": "negative"}
],
"tenable": [
  {"sentiment": "positive"}
],
"tenacious": [
  {"sentiment": "positive"}
],
"tenacity": [
  {"sentiment": "positive"}
],
"tenancy": [
  {"sentiment": "positive"}
],
"tenant": [
  {"sentiment": "positive"}
],
"tender": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"tenderness": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"tenement": [
  {"sentiment": "negative"}
],
"tension": [
  {"sentiment": "anger"}
],
"terminal": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"terminate": [
  {"sentiment": "sadness"}
],
"termination": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"termite": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"terrible": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"terribly": [
  {"sentiment": "sadness"}
],
"terrific": [
  {"sentiment": "sadness"}
],
"terror": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"terrorism": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"terrorist": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"terrorize": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"testament": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"testimony": [
  {"sentiment": "trust"}
],
"tetanus": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"tether": [
  {"sentiment": "negative"}
],
"thankful": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"thanksgiving": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"theft": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"theism": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"theocratic": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"theological": [
  {"sentiment": "trust"}
],
"theology": [
  {"sentiment": "anticipation"}
],
"theorem": [
  {"sentiment": "trust"}
],
"theoretical": [
  {"sentiment": "positive"}
],
"theory": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"therapeutic": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"therapeutics": [
  {"sentiment": "positive"}
],
"thermocouple": [
  {"sentiment": "anticipation"}
],
"thermometer": [
  {"sentiment": "trust"}
],
"thief": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"thinker": [
  {"sentiment": "positive"}
],
"thirst": [
  {"sentiment": "anticipation"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"thirsty": [
  {"sentiment": "negative"}
],
"thirteenth": [
  {"sentiment": "fear"}
],
"thorn": [
  {"sentiment": "negative"}
],
"thorny": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"thoroughbred": [
  {"sentiment": "positive"}
],
"thought": [
  {"sentiment": "anticipation"}
],
"thoughtful": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"thoughtfulness": [
  {"sentiment": "positive"}
],
"thoughtless": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"thrash": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"threat": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"threaten": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"threatening": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"thresh": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"thrift": [
  {"sentiment": "disgust"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"thrill": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"thrilling": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"thriving": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"throb": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"throne": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"throttle": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"thug": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"thump": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"thumping": [
  {"sentiment": "fear"}
],
"thundering": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"thwart": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"tickle": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"tiff": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"tighten": [
  {"sentiment": "anger"}
],
"tiling": [
  {"sentiment": "positive"}
],
"time": [
  {"sentiment": "anticipation"}
],
"timely": [
  {"sentiment": "positive"}
],
"timid": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"timidity": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"tinsel": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"tipsy": [
  {"sentiment": "negative"}
],
"tirade": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"tired": [
  {"sentiment": "negative"}
],
"tiredness": [
  {"sentiment": "negative"}
],
"tiresome": [
  {"sentiment": "negative"}
],
"tit": [
  {"sentiment": "negative"}
],
"title": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"toad": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"toast": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"tobacco": [
  {"sentiment": "negative"}
],
"toilet": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"toils": [
  {"sentiment": "negative"}
],
"tolerant": [
  {"sentiment": "positive"}
],
"tolerate": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"toleration": [
  {"sentiment": "positive"}
],
"tomb": [
  {"sentiment": "sadness"}
],
"tomorrow": [
  {"sentiment": "anticipation"}
],
"toothache": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"top": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"topple": [
  {"sentiment": "surprise"}
],
"torment": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"torn": [
  {"sentiment": "negative"}
],
"tornado": [
  {"sentiment": "fear"}
],
"torpedo": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"torrent": [
  {"sentiment": "fear"}
],
"torrid": [
  {"sentiment": "negative"}
],
"tort": [
  {"sentiment": "negative"}
],
"tortious": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"torture": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"touched": [
  {"sentiment": "negative"}
],
"touchy": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"tough": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"toughness": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"tower": [
  {"sentiment": "positive"}
],
"towering": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"toxic": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"toxin": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"track": [
  {"sentiment": "anticipation"}
],
"tract": [
  {"sentiment": "fear"}
],
"trade": [
  {"sentiment": "trust"}
],
"traditional": [
  {"sentiment": "positive"}
],
"tragedy": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"tragic": [
  {"sentiment": "negative"}
],
"trainer": [
  {"sentiment": "trust"}
],
"traitor": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"tramp": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"trance": [
  {"sentiment": "negative"}
],
"tranquil": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"tranquility": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"transaction": [
  {"sentiment": "trust"}
],
"transcendence": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"transcendental": [
  {"sentiment": "positive"}
],
"transcript": [
  {"sentiment": "trust"}
],
"transgression": [
  {"sentiment": "negative"}
],
"transitional": [
  {"sentiment": "anticipation"}
],
"translation": [
  {"sentiment": "trust"}
],
"trappings": [
  {"sentiment": "negative"}
],
"traps": [
  {"sentiment": "negative"}
],
"trash": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"trashy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"traumatic": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"travail": [
  {"sentiment": "negative"}
],
"traveling": [
  {"sentiment": "positive"}
],
"travesty": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"treacherous": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"treachery": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"treadmill": [
  {"sentiment": "anticipation"}
],
"treason": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"treasure": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"treasurer": [
  {"sentiment": "trust"}
],
"treat": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"tree": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"trembling": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"tremendously": [
  {"sentiment": "positive"}
],
"tremor": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"trend": [
  {"sentiment": "positive"}
],
"trendy": [
  {"sentiment": "positive"}
],
"trepidation": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"trespass": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"tribe": [
  {"sentiment": "trust"}
],
"tribulation": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"tribunal": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"tribune": [
  {"sentiment": "trust"}
],
"tributary": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"tribute": [
  {"sentiment": "positive"}
],
"trick": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"trickery": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"trifle": [
  {"sentiment": "negative"}
],
"trig": [
  {"sentiment": "positive"}
],
"trip": [
  {"sentiment": "surprise"}
],
"tripping": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"triumph": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"triumphant": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"troll": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"trophy": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"troublesome": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"truce": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"truck": [
  {"sentiment": "trust"}
],
"true": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"trump": [
  {"sentiment": "surprise"}
],
"trumpet": [
  {"sentiment": "negative"}
],
"truss": [
  {"sentiment": "trust"}
],
"trust": [
  {"sentiment": "trust"}
],
"trustee": [
  {"sentiment": "trust"}
],
"trusty": [
  {"sentiment": "positive"}
],
"truth": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"truthful": [
  {"sentiment": "trust"}
],
"truthfulness": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"tumble": [
  {"sentiment": "negative"}
],
"tumor": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"tumour": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"tumult": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"tumultuous": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"turbulence": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"turbulent": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"turmoil": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"tussle": [
  {"sentiment": "anger"}
],
"tutelage": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"tutor": [
  {"sentiment": "positive"}
],
"twin": [
  {"sentiment": "positive"}
],
"twinkle": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"twitch": [
  {"sentiment": "negative"}
],
"typhoon": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"tyrannical": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"tyranny": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"tyrant": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ugliness": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ugly": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"ulcer": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ulterior": [
  {"sentiment": "negative"}
],
"ultimate": [
  {"sentiment": "anticipation"},
  {"sentiment": "sadness"}
],
"ultimately": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"ultimatum": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"umpire": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"unable": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unacceptable": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unaccountable": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"unacknowledged": [
  {"sentiment": "sadness"}
],
"unanimity": [
  {"sentiment": "positive"}
],
"unanimous": [
  {"sentiment": "positive"}
],
"unanticipated": [
  {"sentiment": "surprise"}
],
"unapproved": [
  {"sentiment": "negative"}
],
"unassuming": [
  {"sentiment": "positive"}
],
"unattached": [
  {"sentiment": "negative"}
],
"unattainable": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unattractive": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unauthorized": [
  {"sentiment": "negative"}
],
"unavoidable": [
  {"sentiment": "negative"}
],
"unaware": [
  {"sentiment": "negative"}
],
"unbearable": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unbeaten": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"unbelief": [
  {"sentiment": "negative"}
],
"unbelievable": [
  {"sentiment": "negative"}
],
"unbiased": [
  {"sentiment": "positive"}
],
"unborn": [
  {"sentiment": "negative"}
],
"unbreakable": [
  {"sentiment": "positive"}
],
"unbridled": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"unbroken": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"uncanny": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"uncaring": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"uncertain": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"unchangeable": [
  {"sentiment": "negative"}
],
"unclean": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"uncomfortable": [
  {"sentiment": "negative"}
],
"unconscionable": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"unconscious": [
  {"sentiment": "negative"}
],
"unconstitutional": [
  {"sentiment": "negative"}
],
"unconstrained": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"uncontrollable": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"uncontrolled": [
  {"sentiment": "negative"}
],
"uncover": [
  {"sentiment": "surprise"}
],
"undecided": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"underestimate": [
  {"sentiment": "surprise"}
],
"underline": [
  {"sentiment": "positive"}
],
"undermined": [
  {"sentiment": "negative"}
],
"underpaid": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"undersized": [
  {"sentiment": "negative"}
],
"understanding": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"undertaker": [
  {"sentiment": "sadness"}
],
"undertaking": [
  {"sentiment": "anticipation"}
],
"underwrite": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"undesirable": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"undesired": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"undisclosed": [
  {"sentiment": "anticipation"}
],
"undiscovered": [
  {"sentiment": "surprise"}
],
"undivided": [
  {"sentiment": "positive"}
],
"undo": [
  {"sentiment": "negative"}
],
"undoubted": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"}
],
"undying": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"uneasiness": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"uneasy": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"uneducated": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unemployed": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unequal": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unequivocal": [
  {"sentiment": "trust"}
],
"unequivocally": [
  {"sentiment": "positive"}
],
"uneven": [
  {"sentiment": "negative"}
],
"unexpected": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"unexpectedly": [
  {"sentiment": "surprise"}
],
"unexplained": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unfair": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unfairness": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unfaithful": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"unfavorable": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unfinished": [
  {"sentiment": "negative"}
],
"unfold": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"unforeseen": [
  {"sentiment": "surprise"}
],
"unforgiving": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unfortunate": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unfriendly": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unfulfilled": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"unfurnished": [
  {"sentiment": "negative"}
],
"ungodly": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"ungrateful": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"unguarded": [
  {"sentiment": "surprise"}
],
"unhappiness": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unhappy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unhealthy": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unholy": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"unification": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"uniformly": [
  {"sentiment": "positive"}
],
"unimaginable": [
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"unimportant": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unimpressed": [
  {"sentiment": "negative"}
],
"unimproved": [
  {"sentiment": "negative"}
],
"uninfected": [
  {"sentiment": "positive"}
],
"uninformed": [
  {"sentiment": "negative"}
],
"uninitiated": [
  {"sentiment": "negative"}
],
"uninspired": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unintelligible": [
  {"sentiment": "negative"}
],
"unintended": [
  {"sentiment": "surprise"}
],
"unintentional": [
  {"sentiment": "surprise"}
],
"unintentionally": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"uninterested": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"uninteresting": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"uninvited": [
  {"sentiment": "sadness"}
],
"unique": [
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"unison": [
  {"sentiment": "positive"}
],
"unitary": [
  {"sentiment": "positive"}
],
"united": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"unity": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"university": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"unjust": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"unjustifiable": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"unjustified": [
  {"sentiment": "negative"}
],
"unkind": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unknown": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"unlawful": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unlicensed": [
  {"sentiment": "negative"}
],
"unlimited": [
  {"sentiment": "positive"}
],
"unlucky": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unmanageable": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"unnatural": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"unofficial": [
  {"sentiment": "negative"}
],
"unpaid": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unpleasant": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unpopular": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unprecedented": [
  {"sentiment": "surprise"}
],
"unpredictable": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"unprepared": [
  {"sentiment": "negative"}
],
"unpretentious": [
  {"sentiment": "positive"}
],
"unproductive": [
  {"sentiment": "negative"}
],
"unprofitable": [
  {"sentiment": "negative"}
],
"unprotected": [
  {"sentiment": "negative"}
],
"unpublished": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unquestionable": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"unquestionably": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"unquestioned": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"unreliable": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"unrequited": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unresolved": [
  {"sentiment": "anticipation"}
],
"unrest": [
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"unruly": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"unsafe": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"unsatisfactory": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"unsatisfied": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unsavory": [
  {"sentiment": "negative"}
],
"unscathed": [
  {"sentiment": "positive"}
],
"unscrupulous": [
  {"sentiment": "negative"}
],
"unseat": [
  {"sentiment": "sadness"}
],
"unselfish": [
  {"sentiment": "positive"}
],
"unsettled": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"unsightly": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"unsophisticated": [
  {"sentiment": "negative"}
],
"unspeakable": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"unstable": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"unsteady": [
  {"sentiment": "fear"}
],
"unsuccessful": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unsuitable": [
  {"sentiment": "negative"}
],
"unsung": [
  {"sentiment": "negative"}
],
"unsupported": [
  {"sentiment": "negative"}
],
"unsurpassed": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"unsuspecting": [
  {"sentiment": "surprise"}
],
"unsustainable": [
  {"sentiment": "negative"}
],
"unsympathetic": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"untamed": [
  {"sentiment": "negative"}
],
"untenable": [
  {"sentiment": "negative"}
],
"unthinkable": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"untidy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"untie": [
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"untimely": [
  {"sentiment": "negative"}
],
"untitled": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"untold": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"untoward": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"untrained": [
  {"sentiment": "negative"}
],
"untrue": [
  {"sentiment": "negative"}
],
"untrustworthy": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"unverified": [
  {"sentiment": "anticipation"}
],
"unwarranted": [
  {"sentiment": "negative"}
],
"unwashed": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"unwavering": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"unwelcome": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unwell": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"unwillingness": [
  {"sentiment": "negative"}
],
"unwise": [
  {"sentiment": "negative"}
],
"unwitting": [
  {"sentiment": "negative"}
],
"unworthy": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"unyielding": [
  {"sentiment": "negative"}
],
"upheaval": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"uphill": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "positive"}
],
"uplift": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"upright": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"uprising": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"uproar": [
  {"sentiment": "negative"}
],
"upset": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"urchin": [
  {"sentiment": "negative"}
],
"urgency": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"urgent": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"urn": [
  {"sentiment": "sadness"}
],
"usefulness": [
  {"sentiment": "positive"}
],
"useless": [
  {"sentiment": "negative"}
],
"usher": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"usual": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"usurp": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"usurped": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"usury": [
  {"sentiment": "negative"}
],
"utility": [
  {"sentiment": "positive"}
],
"utopian": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"vacancy": [
  {"sentiment": "negative"}
],
"vacation": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"vaccine": [
  {"sentiment": "positive"}
],
"vacuous": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"vague": [
  {"sentiment": "negative"}
],
"vagueness": [
  {"sentiment": "negative"}
],
"vainly": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"valiant": [
  {"sentiment": "positive"}
],
"validity": [
  {"sentiment": "fear"}
],
"valor": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"valuable": [
  {"sentiment": "positive"}
],
"vampire": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"vanguard": [
  {"sentiment": "positive"}
],
"vanish": [
  {"sentiment": "surprise"}
],
"vanished": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"vanity": [
  {"sentiment": "negative"}
],
"vanquish": [
  {"sentiment": "positive"}
],
"variable": [
  {"sentiment": "surprise"}
],
"varicella": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"varicose": [
  {"sentiment": "negative"}
],
"veal": [
  {"sentiment": "sadness"},
  {"sentiment": "trust"}
],
"veer": [
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"vegetative": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"vehement": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"velvet": [
  {"sentiment": "positive"}
],
"velvety": [
  {"sentiment": "positive"}
],
"vendetta": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"venerable": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"veneration": [
  {"sentiment": "positive"}
],
"vengeance": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"vengeful": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"venom": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"venomous": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"vent": [
  {"sentiment": "anger"}
],
"veracity": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"verbosity": [
  {"sentiment": "negative"}
],
"verdant": [
  {"sentiment": "positive"}
],
"verdict": [
  {"sentiment": "fear"}
],
"verge": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"verification": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"verified": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"verily": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"veritable": [
  {"sentiment": "positive"}
],
"vermin": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"vernal": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"versus": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"vertigo": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"verve": [
  {"sentiment": "positive"}
],
"vesicular": [
  {"sentiment": "disgust"}
],
"veteran": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"veto": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"vicar": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"vice": [
  {"sentiment": "negative"}
],
"vicious": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"victim": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"victimized": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"victor": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"victorious": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"victory": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"vigil": [
  {"sentiment": "anticipation"}
],
"vigilance": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"vigilant": [
  {"sentiment": "fear"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"vigor": [
  {"sentiment": "positive"}
],
"vigorous": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"villager": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"villain": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"villainous": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"vindicate": [
  {"sentiment": "anger"}
],
"vindicated": [
  {"sentiment": "positive"}
],
"vindication": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"vindictive": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"violation": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"violence": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"violent": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"violently": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"viper": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"virgin": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"virginity": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"virtue": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"virtuous": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"virulence": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"virus": [
  {"sentiment": "negative"}
],
"vision": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"}
],
"visionary": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"visit": [
  {"sentiment": "positive"}
],
"visitation": [
  {"sentiment": "negative"}
],
"visitor": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"visor": [
  {"sentiment": "anticipation"},
  {"sentiment": "surprise"}
],
"vital": [
  {"sentiment": "positive"}
],
"vitality": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"vivacious": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"vivid": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"vixen": [
  {"sentiment": "negative"}
],
"vocabulary": [
  {"sentiment": "positive"}
],
"volatility": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"volcano": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"volunteer": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"volunteers": [
  {"sentiment": "trust"}
],
"voluptuous": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"vomit": [
  {"sentiment": "disgust"}
],
"vomiting": [
  {"sentiment": "negative"}
],
"voodoo": [
  {"sentiment": "negative"}
],
"vote": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"votive": [
  {"sentiment": "trust"}
],
"vouch": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"voucher": [
  {"sentiment": "trust"}
],
"vow": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"voyage": [
  {"sentiment": "anticipation"}
],
"vulgar": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"vulgarity": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"vulnerability": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"vulture": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"waffle": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wages": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"wail": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wait": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"}
],
"wallow": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wan": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wane": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wanting": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"war": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"warden": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"ware": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"warfare": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"warlike": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"warlock": [
  {"sentiment": "fear"}
],
"warn": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"warned": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "surprise"}
],
"warning": [
  {"sentiment": "fear"}
],
"warp": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"warped": [
  {"sentiment": "negative"}
],
"warranty": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"warrior": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"wart": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"wary": [
  {"sentiment": "fear"}
],
"waste": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"wasted": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"wasteful": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wasting": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"watch": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"}
],
"watchdog": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"watchful": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"watchman": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"waterproof": [
  {"sentiment": "positive"}
],
"watery": [
  {"sentiment": "negative"}
],
"waver": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"weakened": [
  {"sentiment": "negative"}
],
"weakly": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"weakness": [
  {"sentiment": "negative"}
],
"wealth": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"wear": [
  {"sentiment": "negative"},
  {"sentiment": "trust"}
],
"wearily": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"weariness": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"weary": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"weatherproof": [
  {"sentiment": "positive"}
],
"weeds": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"weep": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"weeping": [
  {"sentiment": "sadness"}
],
"weigh": [
  {"sentiment": "anticipation"},
  {"sentiment": "trust"}
],
"weight": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"weighty": [
  {"sentiment": "fear"}
],
"weird": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"weirdo": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"welcomed": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"wen": [
  {"sentiment": "negative"}
],
"wench": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"whack": [
  {"sentiment": "negative"}
],
"whim": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"whimper": [
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"whimsical": [
  {"sentiment": "joy"}
],
"whine": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"whip": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"whirlpool": [
  {"sentiment": "fear"}
],
"whirlwind": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"whisky": [
  {"sentiment": "negative"}
],
"white": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"whiteness": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"wholesome": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"whore": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"wicked": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"wickedness": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"wicket": [
  {"sentiment": "positive"}
],
"widespread": [
  {"sentiment": "positive"}
],
"widow": [
  {"sentiment": "sadness"}
],
"widower": [
  {"sentiment": "sadness"}
],
"wild": [
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"wildcat": [
  {"sentiment": "negative"}
],
"wilderness": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "sadness"}
],
"wildfire": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"willful": [
  {"sentiment": "anger"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"willingly": [
  {"sentiment": "positive"}
],
"willingness": [
  {"sentiment": "positive"}
],
"wimp": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"wimpy": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wince": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"windfall": [
  {"sentiment": "positive"}
],
"winner": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"winning": [
  {"sentiment": "anticipation"},
  {"sentiment": "disgust"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"winnings": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"wireless": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"wis": [
  {"sentiment": "positive"}
],
"wisdom": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"wise": [
  {"sentiment": "positive"}
],
"wishful": [
  {"sentiment": "anticipation"}
],
"wit": [
  {"sentiment": "positive"}
],
"witch": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"witchcraft": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"withdraw": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wither": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"withered": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"}
],
"withstand": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "positive"}
],
"witness": [
  {"sentiment": "trust"}
],
"wits": [
  {"sentiment": "positive"}
],
"witty": [
  {"sentiment": "joy"},
  {"sentiment": "positive"}
],
"wizard": [
  {"sentiment": "anticipation"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"woe": [
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"woeful": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"woefully": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"womb": [
  {"sentiment": "positive"}
],
"wonderful": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"wonderfully": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"wondrous": [
  {"sentiment": "positive"}
],
"wont": [
  {"sentiment": "anticipation"}
],
"wop": [
  {"sentiment": "anger"}
],
"word": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"words": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"working": [
  {"sentiment": "positive"}
],
"worm": [
  {"sentiment": "anticipation"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"worn": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"worried": [
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"worry": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"worrying": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"worse": [
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"worsening": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"worship": [
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"worth": [
  {"sentiment": "positive"}
],
"worthless": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"worthy": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"wot": [
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"wound": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wrangling": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wrath": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"wreak": [
  {"sentiment": "anger"},
  {"sentiment": "negative"}
],
"wreck": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"},
  {"sentiment": "surprise"}
],
"wrecked": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wrench": [
  {"sentiment": "negative"}
],
"wrestling": [
  {"sentiment": "negative"}
],
"wretch": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wretched": [
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wring": [
  {"sentiment": "anger"}
],
"wrinkled": [
  {"sentiment": "sadness"}
],
"writer": [
  {"sentiment": "positive"}
],
"wrong": [
  {"sentiment": "negative"}
],
"wrongdoing": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wrongful": [
  {"sentiment": "anger"},
  {"sentiment": "disgust"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wrongly": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "sadness"}
],
"wrought": [
  {"sentiment": "negative"}
],
"wry": [
  {"sentiment": "negative"}
],
"xenophobia": [
  {"sentiment": "fear"},
  {"sentiment": "negative"}
],
"yawn": [
  {"sentiment": "negative"}
],
"yawning": [
  {"sentiment": "negative"}
],
"yearning": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "negative"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"yell": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"yellows": [
  {"sentiment": "negative"}
],
"yelp": [
  {"sentiment": "anger"},
  {"sentiment": "fear"},
  {"sentiment": "negative"},
  {"sentiment": "surprise"}
],
"young": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"younger": [
  {"sentiment": "positive"}
],
"youth": [
  {"sentiment": "anger"},
  {"sentiment": "anticipation"},
  {"sentiment": "fear"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"}
],
"zany": [
  {"sentiment": "surprise"}
],
"zeal": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "surprise"},
  {"sentiment": "trust"}
],
"zealous": [
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"zest": [
  {"sentiment": "anticipation"},
  {"sentiment": "joy"},
  {"sentiment": "positive"},
  {"sentiment": "trust"}
],
"zip": [
  {"sentiment": "negative"}
]
}
