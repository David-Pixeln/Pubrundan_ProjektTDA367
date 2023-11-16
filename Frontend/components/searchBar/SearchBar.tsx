import React, { useContext, useRef, useState } from 'react';
import { Pressable, TextInput } from "react-native";
import { ThemeContext } from '@constants/themes';

import { SearchBarStyles } from './SearchBar.style';
import * as VectorIcons from '@expo/vector-icons';


export type SearchBarProps = {
  placeholder?: string;
  onChangeText?: (text: string) => void;
};


export function SearchBar({ placeholder = "Search", onChangeText = () => {} }: SearchBarProps) {
  const [text, setText] = useState('');
  const inputRef = useRef<TextInput>(null);

  const theme = useContext(ThemeContext);
  const styles = SearchBarStyles(theme);

  
  const handlePress = () => {
    // Focus text input
    inputRef.current?.focus();
  };

  const handleTextChange = (text: string) => {
    setText(text);
    onChangeText(text);
  }

  const { colors } = useContext(ThemeContext);

  return (
    <Pressable onPress={handlePress} style={styles.pressableContainer}>
      <VectorIcons.Ionicons name="search" size={18} color={colors.text} />
      <TextInput style={styles.input}
        ref={inputRef}
        value={text}
        inputMode='search'
        enterKeyHint='search'
        returnKeyType='search'
        clearButtonMode="always"
        placeholder={placeholder}
        placeholderTextColor={colors.placeholderText}
        onChangeText={handleTextChange}
      />
    </Pressable>
  );
}