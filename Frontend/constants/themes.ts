import { DefaultTheme as ReactDefaultTheme, Theme } from '@react-navigation/native';
import { Neutral, ShadedColor } from '@constants/colors';
import { defaultNeutral, defaultShadedColor } from '@constants/colors';
import { createContext } from 'react';



export type CustomTheme = {
  dark: boolean;
  colors: {
    primary: string;
    secondary: string;
    background: Record<ShadedColor, string>;
    text: string;
    placeholderText: string,
    neutral: Record<Neutral, string>;
    danger: string,
    warning: string,
    success: string,
  };
}


export const DarkTheme: CustomTheme = {
  dark: true,
  colors: {
    primary: '#FED148',
    secondary: '#E63462',
    background: {
      s200: '#1D1F2E',
      brand: '#2A2D43',
      s600: '#414361',
    },
    text: '#FFFFFF',
    placeholderText: defaultNeutral.s300,
    neutral: defaultNeutral,
    danger: '#CF1717',
    warning: '#CF9700',
    success: '#008A09',
  }
}

export const DefaultTheme: CustomTheme = DarkTheme;
export const ThemeContext = createContext<CustomTheme>(DefaultTheme);
