from tkinter import *
from deep_translator import GoogleTranslator

def translate(Input, Lang):
    L = Lang.lower()

    data = {'afrikaans': 'af', 'albanian': 'sq', 'amharic': 'am', 'arabic': 'ar', 'armenian': 'hy', 'assamese': 'as',
            'aymara': 'ay', 'azerbaijani': 'az', 'bambara': 'bm', 'basque': 'eu', 'belarusian': 'be', 'bengali': 'bn',
            'bhojpuri': 'bho', 'bosnian': 'bs', 'bulgarian': 'bg', 'catalan': 'ca', 'cebuano': 'ceb', 'chichewa': 'ny',
            'chinese (simplified)': 'zh-CN', 'chinese (traditional)': 'zh-TW', 'corsican': 'co', 'croatian': 'hr',
            'czech': 'cs', 'danish': 'da', 'dhivehi': 'dv', 'dogri': 'doi', 'dutch': 'nl', 'english': 'en',
            'esperanto': 'eo', 'estonian': 'et', 'ewe': 'ee', 'filipino': 'tl', 'finnish': 'fi', 'french': 'fr',
            'frisian': 'fy', 'galician': 'gl', 'georgian': 'ka', 'german': 'de', 'greek': 'el', 'guarani': 'gn',
            'gujarati': 'gu', 'haitian creole': 'ht', 'hausa': 'ha', 'hawaiian': 'haw', 'hebrew': 'iw', 'hindi': 'hi',
            'hmong': 'hmn', 'hungarian': 'hu', 'icelandic': 'is', 'igbo': 'ig', 'ilocano': 'ilo', 'indonesian': 'id',
            'irish': 'ga', 'italian': 'it', 'japanese': 'ja', 'javanese': 'jw', 'kannada': 'kn', 'kazakh': 'kk',
            'khmer': 'km', 'kinyarwanda': 'rw', 'konkani': 'gom', 'korean': 'ko', 'krio': 'kri',
            'kurdish (kurmanji)': 'ku', 'kurdish (sorani)': 'ckb', 'kyrgyz': 'ky', 'lao': 'lo', 'latin': 'la',
            'latvian': 'lv', 'lingala': 'ln', 'lithuanian': 'lt', 'luganda': 'lg', 'luxembourgish': 'lb',
            'macedonian': 'mk', 'maithili': 'mai', 'malagasy': 'mg', 'malay': 'ms', 'malayalam': 'ml', 'maltese': 'mt',
            'maori': 'mi', 'marathi': 'mr', 'meiteilon (manipuri)': 'mni-Mtei', 'mizo': 'lus', 'mongolian': 'mn',
            'myanmar': 'my', 'nepali': 'ne', 'norwegian': 'no', 'odia (oriya)': 'or', 'oromo': 'om', 'pashto': 'ps',
            'persian': 'fa', 'polish': 'pl', 'portuguese': 'pt', 'punjabi': 'pa', 'quechua': 'qu', 'romanian': 'ro',
            'russian': 'ru', 'samoan': 'sm', 'sanskrit': 'sa', 'scots gaelic': 'gd', 'sepedi': 'nso',
            'serbian': 'sr', 'sesotho': 'st', 'shona': 'sn', 'sindhi': 'sd', 'sinhala': 'si', 'slovak': 'sk',
            'slovenian': 'sl', 'somali': 'so', 'spanish': 'es', 'sundanese': 'su', 'swahili': 'sw', 'swedish': 'sv',
            'tajik': 'tg', 'tamil': 'ta', 'tatar': 'tt', 'telugu': 'te', 'thai': 'th', 'tigrinya': 'ti', 'tsonga': 'ts',
            'turkish': 'tr', 'turkmen': 'tk', 'twi': 'ak', 'ukrainian': 'uk', 'urdu': 'ur', 'uyghur': 'ug',
            'uzbek': 'uz', 'vietnamese': 'vi', 'welsh': 'cy', 'xhosa': 'xh', 'yiddish': 'yi', 'yoruba': 'yo',
            'zulu': 'zu'}

    # Translate using GoogleTranslator
    t = GoogleTranslator(source='auto', target=data[L])
    return t.translate(Input)

def work():
    # Get inputs from the Entry widgets
    Input = enter.get()
    Lang = enter2.get()

    # Call the translate function
    result = translate(Input, Lang)

    # Display the result in enter3
    enter3.delete(0, END)  # Clear any existing text
    enter3.insert(0, result)  # Insert the new result

# Create the GUI
app = Tk()
app.geometry('310x310')
app.title('GUI Translator (English to other Languages)')

# Label and Entry for text to be translated
label1 = Label(app, text="Enter a word/sentence:", font=("Arial", 15))
label1.grid(row=0, column=0, columnspan=2, padx=5, pady=5)

enter = Entry(app, width=30, font=('Arial', 15))
enter.grid(row=1, column=0, columnspan=2, padx=5, pady=5)

# Label and Entry for target language
label2 = Label(app, text="Enter the target language:", font=("Arial", 15))
label2.grid(row=2, column=0, columnspan=2, padx=5, pady=5)

enter2 = Entry(app, width=30, font=('Arial', 15))
enter2.grid(row=3, column=0, columnspan=2, padx=5, pady=5)

# Label and Entry for displaying the result
label3 = Label(app, text="Translation Output:", font=("Arial", 15))
label3.grid(row=4, column=0, columnspan=2, padx=5, pady=5)

enter3 = Entry(app, width=30, font=('Arial', 15))
enter3.grid(row=5, column=0, columnspan=2, padx=5, pady=5)

# Button to trigger the translation
submit = Button(app, text='Translate', font=('Ink Free', 20), command=work)
submit.grid(row=6, column=0, columnspan=2, pady=20)


app.mainloop()