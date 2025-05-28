import React, { useState, useRef, useEffect } from 'react';
import { Message, User } from '../../types/Chat';
import Header from '../../components/common/Chat/Header.tsx';

interface ChatWindowProps {
    messages: Message[];
    currentUser: User;
    activeContact: User;
    onSendMessage: (text: string) => void;
}

const ChatWindow: React.FC<ChatWindowProps> = ({
                                                   messages,
                                                   currentUser,
                                                   activeContact,
                                                   onSendMessage
                                               }) => {
    const [newMessage, setNewMessage] = useState('');
    const messagesEndRef = useRef<HTMLDivElement>(null);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    };

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    const handleSendMessage = () => {
        if (newMessage.trim()) {
            onSendMessage(newMessage);
            setNewMessage('');
        }
    };

    const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') {
            handleSendMessage();
        }
    };

    return (
        <div className="flex flex-col h-full">
            <Header user={activeContact} />

            <div className="flex-1 overflow-y-auto p-6">
                {messages.map((message) => {
                    const isCurrentUser = message.senderId === currentUser.id;

                    return (
                        <div
                            key={message.id}
                            className={`mb-4 flex ${isCurrentUser ? 'justify-end' : 'justify-start'}`}
                        >
                            <div
                                className={`
                  max-w-[480px] p-3 rounded-2xl
                  ${isCurrentUser
                                    ? 'bg-primary-background bg-opacity-80 text-white rounded-bl-sm' :'bg-secondary-dark text-black rounded-br-sm'
                                }
                `}
                            >
                                <p className="text-base font-medium">{message.text}</p>
                            </div>
                        </div>
                    );
                })}
                <div ref={messagesEndRef} />
            </div>

            <div className="p-6">
                <div className="flex items-center bg-accent-teal bg-opacity-30 rounded-lg border border-border-primary">
                    <input
                        type="text"
                        value={newMessage}
                        onChange={(e) => setNewMessage(e.target.value)}
                        onKeyDown={handleKeyDown}
                        placeholder="escriba su mensaje"
                        className="flex-1 bg-transparent py-2 px-4 text-base outline-none"
                    />
                    <button className="p-2 mx-1">
                        <img src="/images/img_smile.svg" alt="Emoji" className="w-6 h-6" />
                    </button>
                    <button className="p-2 mx-1">
                        <img src="/images/img_image.svg" alt="Attach" className="w-6 h-6" />
                    </button>
                    <button
                        className="p-2 mx-1 text-accent-teal"
                        onClick={handleSendMessage}
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                            <line x1="22" y1="2" x2="11" y2="13"></line>
                            <polygon points="22 2 15 22 11 13 2 9 22 2"></polygon>
                        </svg>
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ChatWindow;