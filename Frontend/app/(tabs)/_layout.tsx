import { useContext } from 'react';
import { Tabs } from 'expo-router';
import { ThemeContext } from '@constants/themes';

import { Icon } from '@components/icon';
import * as VectorIcons from '@expo/vector-icons';
import { SearchBarWithFilter, SearchBarStyles } from '@components/searchBar';


export default function TabsLayout() {
  const theme = useContext(ThemeContext);
  const colors = theme.colors;
  const searchBarStyles = SearchBarStyles(theme);

  return (
    <Tabs
      initialRouteName='search'
      screenOptions={{
        headerShown: false,
        headerStyle: {
          backgroundColor: colors.background.s600,
        },
        
        tabBarStyle: {
          backgroundColor: colors.background.s600,
        },
        tabBarShowLabel: false,
        tabBarActiveTintColor: colors.primary,
        tabBarInactiveTintColor: colors.text,
    }}
    >

      <Tabs.Screen name="index" 
        options={{
          tabBarIcon: ({ color }) => <Icon Icon={VectorIcons.FontAwesome} name="home" color={color} />,
        }}
      />

      <Tabs.Screen name="search"
        options={{
          headerShown: true,
          headerTitle: () => <SearchBarWithFilter />,
          headerTitleAlign: 'center',
          headerTitleContainerStyle: searchBarStyles.container,
          
          tabBarIcon: ({ color }) => <Icon Icon={VectorIcons.FontAwesome} name="search" size={24} color={color} />
        }}
      />

      <Tabs.Screen name="explore"
        options={{
          tabBarIcon: ({ color }) => <Icon Icon={VectorIcons.Ionicons} name="navigate" color={color} />
        }}
      />

      <Tabs.Screen name="friends"
        options={{
          tabBarIcon: ({ color }) => <Icon Icon={VectorIcons.FontAwesome5} name="user-friends" size={24} color={color} />
        }}
      />

      <Tabs.Screen name="profile"
        options={{
          tabBarIcon: ({ color }) => <Icon Icon={VectorIcons.FontAwesome} name="user" size={24} color={color} />,
        }}
      />

    </Tabs>
  );
}