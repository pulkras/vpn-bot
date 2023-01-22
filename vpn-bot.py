import telebot
import config

from telebot import types

bot = telebot.TeleBot(config.code)

@bot.message_handler(commands=['start'])
def welcome(message):

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    item = types.KeyboardButton("configure vpn")

    markup.add(item)

    bot.send_message(message.chat.id, "Welcome to telegram vpn bot!", parse_mode="html", reply_markup=markup)

@bot.message_handler(content_types=['text'])
def reactions(message):
    if message.chat.type == "private":
        if message.text == "configure vpn":
            markup = types.InlineKeyboardMarkup(row_width=3)
            first_item = types.InlineKeyboardButton("download app", callback_data="app")
            second_item = types.InlineKeyboardButton("get config as file", callback_data="config")
            third_item = types.InlineKeyboardButton("get by qr", callback_data="qr")

            markup.add(first_item, second_item, third_item)
            bot.send_message(message.chat.id, "OK! Now choose a method to use vpn", parse_mode="html", reply_markup=markup)

@bot.callback_query_handler(func=lambda call: True)
def callback_inline(call):
    try:
        if call.message:
            if call.data == "app":
                bot.send_message(call.message.chat.id, "future app")
            elif call.data == "config":
                bot.send_message(call.message.chat.id, "future file")
            elif call.data == "qr":
                bot.send_message(call.message.chat.id, "future qr code")
            
            bot.send_message(call.message.chat.id, "try to configure vpn. If you cannot, write ...")

    except Exception as e:
        print(repr(e))

bot.polling(none_stop=True)
