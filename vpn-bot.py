import telebot
import config

bot = telebot.TeleBot(config.code)

@bot.message_handler(commands=['start'])
def welcome(message):
    bot.send_message(message.chat.id, "Welcome to telegram vpn bot!")

bot.polling(none_stop=True)
